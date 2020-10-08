package my.projects.seasonalanimetracker.schedule.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.db.dao.ScheduleDAO
import my.projects.seasonalanimetracker.schedule.data.ScheduleDataUtils
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.domain.mapper.db.ScheduleDataToEntityMapper
import my.projects.seasonalanimetracker.schedule.domain.mapper.db.ScheduleEntityToDataMapper
import timber.log.Timber
import java.time.DayOfWeek
import javax.inject.Inject

interface IScheduleDataSource {
    fun getSchedule(): Observable<Map<DayOfWeek, List<ScheduleMediaItem>>>
    fun updateSchedule(): Completable
    fun updateLocalMediaStatus(media: Media, status: String): Completable
}

class ScheduleDataSource @Inject constructor (
    private val scheduleDAO: ScheduleDAO,
    private val entityToDataMapper: ScheduleEntityToDataMapper,
    private val dataToEntityMapper: ScheduleDataToEntityMapper,
    private val loader: IScheduleLoader,
    private val dateSource: IScheduleDateSource
): IScheduleDataSource {

    override fun getSchedule(): Observable<Map<DayOfWeek, List<ScheduleMediaItem>>> {
        return scheduleDAO.getSchedule().map {
            Timber.d("Got ${it.size} entities")
            it.map { entityToDataMapper.map(it) }
        }.map {
            schedulesToDayOfWeekMap(it)
        } .subscribeOn(Schedulers.io())
    }

    override fun updateSchedule(): Completable {
        return if (isDateAfterLastUpdateDate()) {
            Timber.d("Clearing schedule and updating schedule")
            clearSchedules().andThen(saveUpdateDate()).andThen(loadSchedule())
        } else {
            Timber.d("Updating schedule")
            loadSchedule()
        }
    }

    private fun isDateAfterLastUpdateDate(): Boolean {
        return dateSource.getCurrentDate().after(dateSource.getLastUpdateDate())
    }

    private fun clearSchedules(): Completable {
        return Completable.fromAction { scheduleDAO.clearSchedules() }.subscribeOn(Schedulers.io())
    }

    private fun saveUpdateDate(): Completable {
        return Completable.fromAction { dateSource.saveUpdateDate(dateSource.getCurrentDate()) }.subscribeOn(Schedulers.io())
    }

    private fun loadSchedule(): Completable {
        return loader.loadSchedule(dateSource.getStartDate(), dateSource.getEndDate()).flatMapCompletable { schedule ->
            storeSchedule(schedule)
        }.subscribeOn(Schedulers.io())
    }

    private fun storeSchedule(schedule: List<ScheduleMediaItem>): Completable {
        Timber.d("Storing ${schedule.size} items")
        return Completable.fromAction {
            scheduleDAO.saveSchedule(schedule.map { dataToEntityMapper.map(it) })
        }.subscribeOn(Schedulers.io())
    }

    private fun schedulesToDayOfWeekMap(schedules: List<ScheduleMediaItem>): Map<DayOfWeek, List<ScheduleMediaItem>> {
        val startDateDayOfWeek = ScheduleDataUtils.dayOfWeekFromMillis(dateSource.getStartDate().timeInMillis)
        val orderedDaysOfWeek = DayOfWeek.values().asList().toMutableList()
        while (orderedDaysOfWeek.first() != startDateDayOfWeek) {
            orderedDaysOfWeek.add(orderedDaysOfWeek.removeAt(0))
        }
        val map = LinkedHashMap<DayOfWeek, MutableList<ScheduleMediaItem>>()
        for (dayOfWeek in orderedDaysOfWeek) {
            map[dayOfWeek] = mutableListOf()
            for (schedule in schedules) {
                val scheduleDayOfWeek = ScheduleDataUtils.dayOfWeekFromMillis(schedule.airingAt)
                if (scheduleDayOfWeek == dayOfWeek) {
                    map[dayOfWeek]!!.add(schedule)
                }
            }
            val list = map[dayOfWeek]
            list!!.sortBy { scheduleMediaItem -> scheduleMediaItem.airingAt }
            map[dayOfWeek] = list
        }
        return map
    }

    override fun updateLocalMediaStatus(media: Media, status: String): Completable {
        return Completable.fromAction {
            scheduleDAO.updateFollowStatus(media.id, status)
        }.subscribeOn(Schedulers.io())
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ScheduleDataSourceModule {
    @Binds
    abstract fun bindScheduleDataSource(scheduleDataSource: ScheduleDataSource): IScheduleDataSource
}