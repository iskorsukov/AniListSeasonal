package my.projects.seasonalanimetracker.schedule.data

import my.projects.seasonalanimetracker.app.common.data.media.Media
import java.io.Serializable

data class ScheduleMediaItem(
    val id: Long,
    val airingAt: Long,
    val episode: Int,
    val media: Media
): Serializable