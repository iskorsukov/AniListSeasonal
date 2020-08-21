package my.projects.seasonalanimetracker.app.common.data.staff

import java.io.Serializable

data class MediaStaff(
    val id: Long,
    val staff: Staff,
    val role: String
): Serializable