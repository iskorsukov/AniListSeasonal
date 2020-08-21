package my.projects.seasonalanimetracker.schedule.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.db.dao.MediaDAO
import my.projects.seasonalanimetracker.db.dao.ScheduleDAO
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.domain.mapper.db.ScheduleDataToEntityMapper
import my.projects.seasonalanimetracker.schedule.domain.mapper.db.ScheduleEntityToDataMapper
import timber.log.Timber
import java.util.*
import javax.inject.Inject

interface IScheduleDataSource {
    fun getSchedule(): Observable<List<ScheduleMediaItem>>
    fun updateSchedule(startDate: Calendar, endDate: Calendar): Completable
}

class ScheduleDataSource @Inject constructor (
    private val scheduleDAO: ScheduleDAO,
    private val entityToDataMapper: ScheduleEntityToDataMapper,
    private val dataToEntityMapper: ScheduleDataToEntityMapper,
    private val loader: IScheduleLoader
): IScheduleDataSource {

    override fun getSchedule(): Observable<List<ScheduleMediaItem>> {
        return scheduleDAO.getSchedule().map {
            Timber.d("Got ${it.size} entities")
            it.map { entityToDataMapper.map(it) }
        }.subscribeOn(Schedulers.io())
    }

    override fun updateSchedule(startDate: Calendar, endDate: Calendar): Completable {
        return loader.loadSchedule(startDate, endDate).flatMapCompletable { schedule ->
            storeSchedule(schedule)
        }.subscribeOn(Schedulers.io())
    }

    private fun storeSchedule(schedule: List<ScheduleMediaItem>): Completable {
        Timber.d("Storing ${schedule.size} items")
        return Completable.fromAction {
            scheduleDAO.saveSchedule(schedule.map { dataToEntityMapper.map(it) })
        }
    }

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ScheduleDataSourceModule {
    @Binds
    abstract fun bindScheduleDataSource(scheduleDataSource: ScheduleDataSource): IScheduleDataSource
}