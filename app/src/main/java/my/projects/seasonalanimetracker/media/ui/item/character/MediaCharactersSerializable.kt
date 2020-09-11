package my.projects.seasonalanimetracker.media.ui.item.character

import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import java.io.Serializable

data class MediaCharactersSerializable(
    val character: ArrayList<MediaCharacter>
): Serializable