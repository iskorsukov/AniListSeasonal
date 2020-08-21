package my.projects.seasonalanimetracker.schedule.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import io.reactivex.disposables.CompositeDisposable
import my.projects.seasonalanimetracker.schedule.data.ScheduleDataUtils
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.domain.IScheduleDataSource
import my.projects.seasonalanimetracker.schedule.domain.IScheduleDateSource
import my.projects.seasonalanimetracker.schedule.viewobject.IScheduleVO
import my.projects.seasonalanimetracker.schedule.viewobject.ScheduleVO
import java.time.DayOfWeek
import kotlin.math.abs

abstract class IScheduleViewModel: ViewModel() {
    abstract fun scheduleLD(): LiveData<IScheduleVO>
    abstract fun updateSchedule()
}

class ScheduleViewModel @ViewModelInject constructor (
    private val scheduleDataSource: IScheduleDataSource,
    private val dateSource: IScheduleDateSource
): IScheduleViewModel() {

    private var scheduleDisposable = CompositeDisposable()

    private val scheduleLD = MutableLiveData<IScheduleVO>().also {
        val disposable = scheduleDataSource.getSchedule().subscribe { schedules ->
            it.postValue(ScheduleVO(schedulesToDayOfWeekMap(schedules)))
        }
        scheduleDisposable.add(disposable)
    }

    override fun scheduleLD(): LiveData<IScheduleVO> {
        return scheduleLD
    }

    override fun updateSchedule() {
        val disposable = scheduleDataSource.updateSchedule(dateSource.getStartDate(), dateSource.getEndDate()).subscribe(
            {},
            { throwable: Throwable? -> throwable?.printStackTrace() }
        )
        scheduleDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        scheduleDisposable.dispose()
    }

    private fun schedulesToDayOfWeekMap(schedules: List<ScheduleMediaItem>): Map<DayOfWeek, List<ScheduleMediaItem>> {
        val startDateDayOfWeek = ScheduleDataUtils.dayOfWeekFromMillis(dateSource.getStartDate().timeInMillis)
        val orderedDaysOfWeek = DayOfWeek.values().asList().toMutableList()
        while (orderedDaysOfWeek.first() != startDateDayOfWeek) {
            orderedDaysOfWeek.add(orderedDaysOfWeek.removeAt(0))
        }
        val map = mutableMapOf<DayOfWeek, MutableList<ScheduleMediaItem>>()
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
}

@Module
@InstallIn(FragmentComponent::class)
abstract class ScheduleViewModelModule {
    @Binds
    abstract fun bindsScheduleViewModel(scheduleViewModel: ScheduleViewModel): IScheduleViewModel
}
