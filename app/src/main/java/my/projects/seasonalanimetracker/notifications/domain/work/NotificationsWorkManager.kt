package my.projects.seasonalanimetracker.notifications.domain.work

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface INotificationWorkManager {
    fun startNotificationCheckWork()
}

class NotificationsWorkManager @Inject constructor(
    @ApplicationContext private val context: Context
): INotificationWorkManager {

    override fun startNotificationCheckWork() {
        WorkManager.getInstance(context).enqueue(getWorkRequest())
    }

    private fun getWorkRequest(): WorkRequest {
        return OneTimeWorkRequestBuilder<NotificationCheckWorker>()
            .build()
    }
}

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class NotificationWorkManagerModule {
    @Binds
    abstract fun bindsNotificationWorkManager(notificationsWorkManager: NotificationsWorkManager): INotificationWorkManager
}