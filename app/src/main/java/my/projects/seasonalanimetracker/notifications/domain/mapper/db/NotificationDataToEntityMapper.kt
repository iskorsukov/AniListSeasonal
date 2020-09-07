package my.projects.seasonalanimetracker.notifications.domain.mapper.db

import my.projects.seasonalanimetracker.app.common.domain.mapper.MediaDataToEntityMapper
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItem
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItemEntity
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import javax.inject.Inject

class NotificationDataToEntityMapper @Inject constructor(): MediaDataToEntityMapper() {

    fun map(notification: NotificationMediaItem): DBNotificationItemEntity {
        return DBNotificationItemEntity(
            DBNotificationItem(
                notification.id,
                notification.media.id,
                notification.createdAt / 1000,
                notification.episode
            ),
            mapMediaEntity(notification.media)
        )
    }
}