package my.projects.seasonalanimetracker.notifications.domain.work

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.db.dao.NotificationsDAO
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import my.projects.seasonalanimetracker.notifications.domain.INotificationsLoader
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationDataToEntityMapper
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationEntityToDataMapper
import timber.log.Timber

class NotificationCheckWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val notificationsDAO: NotificationsDAO,
    private val notificationsLoader: INotificationsLoader,
    private val notificationEntityToDataMapper: NotificationEntityToDataMapper,
    private val notificationDataToEntityMapper: NotificationDataToEntityMapper
): RxWorker(context, workerParameters) {

    override fun createWork(): Single<Result> {
        return hasUnreadNotifications().flatMap { hasUnread ->
            if (hasUnread) {
                Timber.d("Found unread notifications")
                Single.zip(
                    notificationsLoader.loadNotifications().subscribeOn(Schedulers.io()),
                    getStoredNotifications().subscribeOn(Schedulers.io()),
                    BiFunction { loadedNotifications: List<NotificationMediaItem>, storedNotifications: List<NotificationMediaItem> ->
                        loadedNotifications.filter { item -> storedNotifications.contains(item) }
                    }
                )
            } else {
                Timber.d("No unread notifications")
                Single.just(emptyList())
            }
        }.flatMapCompletable { newNotifications ->
            raiseNotifications(newNotifications).andThen(storeNotifications(newNotifications))
        }.toSingle {
            Result.success()
        }
    }

    private fun hasUnreadNotifications(): Single<Boolean> {
        return notificationsLoader.loadUnreadNotificationsCount().map { count -> count > 0 }
    }

    private fun getStoredNotifications(): Single<List<NotificationMediaItem>> {
        return notificationsDAO.getNotifications().map {
            it.map { notificationEntityToDataMapper.map(it) }
        }
    }

    private fun raiseNotifications(notifications: List<NotificationMediaItem>): Completable {
        // TODO implement
        return Completable.complete()
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
}