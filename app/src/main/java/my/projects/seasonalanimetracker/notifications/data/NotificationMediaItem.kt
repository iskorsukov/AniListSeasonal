package my.projects.seasonalanimetracker.notifications.data

import my.projects.seasonalanimetracker.app.common.data.media.Media
import java.io.Serializable

data class NotificationMediaItem(
    val id: Long,
    val createdAt: Long,
    val episode: Int,
    val media: Media
): Serializable