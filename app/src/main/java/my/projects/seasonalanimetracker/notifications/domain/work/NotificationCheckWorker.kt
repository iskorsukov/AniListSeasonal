package my.projects.seasonalanimetracker.notifications.domain.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.db.dao.NotificationsDAO
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import my.projects.seasonalanimetracker.notifications.domain.INotificationsLoader
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationDataToEntityMapper
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationEntityToDataMapper
import timber.log.Timber
import java.text.DateFormat
import java.util.*
import kotlin.random.Random

class NotificationCheckWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val notificationsDAO: NotificationsDAO,
    private val notificationsLoader: INotificationsLoader,
    private val notificationEntityToDataMapper: NotificationEntityToDataMapper,
    private val notificationDataToEntityMapper: NotificationDataToEntityMapper
): RxWorker(context, workerParameters) {

    companion object {
        const val CHANNEL_ID = "airing_notifications"
        const val CHANNEL_NAME = "Airing Shows Notifications"
        const val CHANNEL_DESC = "Notifications for shows that aired recently"
    }

    override fun createWork(): Single<Result> {
        return getUnreadNotificationsCount().flatMap { unread ->
            if (unread > 0) {
                Timber.d("Found unread notifications")
                notificationsLoader.loadNotifications(unread).subscribeOn(Schedulers.io())
            } else {
                Timber.d("No unread notifications")
                Single.just(emptyList())
            }
        }.flatMapCompletable { newNotifications ->
            when {
                newNotifications.isEmpty() -> {
                    Completable.complete()
                }
                newNotifications.size > 3 -> {
                    raiseGroupNotification(newNotifications).andThen(storeNotifications(newNotifications))
                }
                else -> {
                    raiseNotifications(newNotifications).andThen(storeNotifications(newNotifications))
                }
            }
        }.toSingle {
            Result.success()
        }
    }

    private fun getUnreadNotificationsCount(): Single<Int> {
        return notificationsLoader.loadUnreadNotificationsCount()
    }

    private fun getStoredNotifications(): Single<List<NotificationMediaItem>> {
        return notificationsDAO.getNotifications().map {
            it.map { notificationEntityToDataMapper.map(it) }
        }
    }

    private fun raiseNotifications(notifications: List<NotificationMediaItem>): Completable {
        Timber.d("Raising ${notifications.size} notifications")
        return Completable.fromAction {
            createNotificationChannel()
            val createdNotifications = notifications.map { createNotification(it) }
            with (NotificationManagerCompat.from(applicationContext)) {
                createdNotifications.forEach {
                    notify(Random.nextInt(), it)
                }
            }
        }
    }

    private fun raiseGroupNotification(notifications: List<NotificationMediaItem>): Completable {
        Timber.d("Raising ${notifications.size} a group notification")
        return Completable.fromAction {
            createNotificationChannel()
            val createdNotification = createGroupNotification(notifications)
            with (NotificationManagerCompat.from(applicationContext)) {
                notify(Random.nextInt(), createdNotification)
            }
        }
    }

    private fun storeNotifications(notifications: List<NotificationMediaItem>): Completable {
        if (notifications.isEmpty()) {
            return Completable.complete()
        }
        return Completable.fromAction {
            notificationsDAO
            .saveNotifications(
                notifications.map { notificationDataToEntityMapper.map(it) }
                )
            }
            .subscribeOn(Schedulers.io())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESC
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(notification: NotificationMediaItem): Notification {
        val formattedTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(
            Date(notification.createdAt)
        )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentTitle(notification.media.titleEnglish ?: notification.media.titleRomaji ?: notification.media.titleNative ?: "Show aired")
            .setContentText("Ep ${notification.episode} just aired at $formattedTime")
            .setSmallIcon(R.drawable.ic_notifications_white)
            .setAutoCancel(true)

        return builder.build()
    }

    private fun createGroupNotification(notifications: List<NotificationMediaItem>): Notification {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentTitle("New notifications")
            .setContentText("You have ${notifications.size} unread notifications")
            .setSmallIcon(R.drawable.ic_notifications_white)
            .setAutoCancel(true)

        return builder.build()
    }

}