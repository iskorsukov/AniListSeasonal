package my.projects.seasonalanimetracker.notifications.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Single
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import javax.inject.Inject

interface INotificationsLoader {
    fun loadNotifications(): Single<List<NotificationMediaItem>>
}

class NotificationsLoader @Inject constructor(
    private val queryClient: INotificationsQueryClient
): INotificationsLoader {
    override fun loadNotifications(): Single<List<NotificationMediaItem>> {
        return queryClient.getPage()
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NotificationsLoaderModule {
    @Binds
    abstract fun bindsNotificationsLoader(notificationsLoader: NotificationsLoader): INotificationsLoader
}