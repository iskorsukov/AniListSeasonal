package my.projects.seasonalanimetracker.app.common.data.characters

import java.io.Serializable

data class MediaCharacter(
    val id: Long,
    val character: Character,
    val voiceActor: VoiceActor?
): Serializable