package my.projects.seasonalanimetracker.notifications.data

import my.projects.seasonalanimetracker.app.common.data.media.Media

data class NotificationMediaItem(
    val id: Long,
    val createdAt: Long,
    val episode: Int,
    val media: Media
)