package my.projects.seasonalanimetracker.app.common.data.staff

import java.io.Serializable

data class Staff(
    val id: Long,
    val name: String,
    val imageUrl: String?
): Serializable