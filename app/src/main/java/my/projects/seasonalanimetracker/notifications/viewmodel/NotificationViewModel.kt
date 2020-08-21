package my.projects.seasonalanimetracker.notifications.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import io.reactivex.disposables.CompositeDisposable
import my.projects.seasonalanimetracker.notifications.domain.INotificationsDataSource
import my.projects.seasonalanimetracker.notifications.viewobject.INotificationsVO
import my.projects.seasonalanimetracker.notifications.viewobject.PagedNotificationsVO

abstract class INotificationViewModel: ViewModel() {
    abstract fun notificationsLD(): LiveData<INotificationsVO>
    abstract fun updateNotifications()
}

class NotificationViewModel @ViewModelInject constructor(
    private val notificationsDataSource: INotificationsDataSource
) : INotificationViewModel() {

    private val notificationsDisposable = CompositeDisposable()

    private val notificationsLD = notificationsDataSource.getNotifications().map {
        PagedNotificationsVO(it) as INotificationsVO
    }

    override fun notificationsLD(): LiveData<INotificationsVO> {
        return notificationsLD
    }

    override fun updateNotifications() {
        val disposable = notificationsDataSource.updateNotifications().subscribe()
        notificationsDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        notificationsDisposable.dispose()
    }
}

@Module
@InstallIn(FragmentComponent::class)
abstract class NotificationsViewModelModule {
    @Binds
    abstract fun bindsNotificationViewModel(notificationViewModel: NotificationViewModel): INotificationViewModel
}