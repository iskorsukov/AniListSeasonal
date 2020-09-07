package my.projects.seasonalanimetracker.following.data

import my.projects.seasonalanimetracker.app.common.data.media.Media
import java.io.Serializable

data class FollowingMediaItem(
    val id: Long,
    val status: String,
    val media: Media
): Serializable