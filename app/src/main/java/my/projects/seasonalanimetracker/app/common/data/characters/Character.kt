package my.projects.seasonalanimetracker.app.common.data.characters

import java.io.Serializable

data class Character(
    val id: Long,
    val name: String,
    val imageUrl: String?
): Serializable