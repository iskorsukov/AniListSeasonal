package my.projects.seasonalanimetracker.notifications.domain.mapper.db

import my.projects.seasonalanimetracker.app.common.domain.mapper.MediaEntityToDataMapper
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItemEntity
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import javax.inject.Inject

class NotificationEntityToDataMapper @Inject constructor(): MediaEntityToDataMapper() {

    fun map(entity: DBNotificationItemEntity): NotificationMediaItem {
        return NotificationMediaItem(
            id = entity.notificationItem.id,
            createdAt = entity.notificationItem.createdAt * 1000,
            media = mapMedia(entity.mediaEntity),
            episode = entity.notificationItem.episode
        )
    }
}