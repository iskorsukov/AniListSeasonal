package my.projects.seasonalanimetracker.app.common.data.characters

import java.io.Serializable

data class VoiceActor(
    val id: Long,
    val name: String,
    val language: String,
    val imageUrl: String?
): Serializable