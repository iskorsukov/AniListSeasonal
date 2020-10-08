package my.projects.seasonalanimetracker.notifications.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Single
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import javax.inject.Inject

interface INotificationsLoader {
    fun loadNotifications(): Single<List<NotificationMediaItem>>
    fun loadNotifications(size: Int): Single<List<NotificationMediaItem>>
    fun loadUnreadNotificationsCount(): Single<Int>
}

class NotificationsLoader @Inject constructor(
    private val queryClient: INotificationsQueryClient
): INotificationsLoader {
    override fun loadNotifications(): Single<List<NotificationMediaItem>> {
        return queryClient.getPage()
    }

    override fun loadNotifications(size: Int): Single<List<NotificationMediaItem>> {
        return queryClient.getPage(size)
    }

    override fun loadUnreadNotificationsCount(): Single<Int> {
        return  queryClient.getUnreadNotificationsCount()
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class NotificationsLoaderModule {
    @Binds
    abstract fun bindsNotificationsLoader(notificationsLoader: NotificationsLoader): INotificationsLoader
}