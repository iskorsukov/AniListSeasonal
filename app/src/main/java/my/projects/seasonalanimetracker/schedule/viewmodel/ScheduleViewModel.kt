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
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.ui.media.status.SelectMediaListActionListener
import my.projects.seasonalanimetracker.auth.domain.IAuthDataSource
import my.projects.seasonalanimetracker.following.data.MediaListAction
import my.projects.seasonalanimetracker.following.domain.FollowingDataSource
import my.projects.seasonalanimetracker.following.domain.IFollowingDataSource
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
    private val followingDataSource: IFollowingDataSource,
    private val authDataSource: IAuthDataSource
): IScheduleViewModel(), SelectMediaListActionListener {

    private var scheduleDisposable = CompositeDisposable()

    private val scheduleLD: LiveData<IScheduleVO> by lazy {
        MutableLiveData<IScheduleVO>().also {
            val disposable = scheduleDataSource.getSchedule().subscribe { schedules ->
                it.postValue(ScheduleVO(schedules, authDataSource.isAuthorized()))
            }
            scheduleDisposable.add(disposable)
        }
    }

    override fun scheduleLD(): LiveData<IScheduleVO> {
        return scheduleLD
    }

    override fun updateSchedule() {
        val disposable = scheduleDataSource.updateSchedule().subscribe(
            {},
            { throwable: Throwable? -> throwable?.printStackTrace() }
        )
        scheduleDisposable.add(disposable)
    }

    override fun onMediaStatusSelected(item: Media, action: MediaListAction) {
        if (action == MediaListAction.REMOVE) {
            followingDataSource.removeFromFollowing(item).subscribe()
        } else if (item.userStatus == null) {
            followingDataSource.addFollowStatus(item, action.name).andThen(scheduleDataSource.updateLocalMediaStatus(item, action.name)).subscribe()
        } else {
            followingDataSource.updateFollowStatus(item, action.name).subscribe()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scheduleDisposable.dispose()
    }
}

@Module
@InstallIn(FragmentComponent::class)
abstract class ScheduleViewModelModule {
    @Binds
    abstract fun bindsScheduleViewModel(scheduleViewModel: ScheduleViewModel): IScheduleViewModel
}
