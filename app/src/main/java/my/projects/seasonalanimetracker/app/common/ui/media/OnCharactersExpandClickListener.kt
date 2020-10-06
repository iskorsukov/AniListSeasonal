package my.projects.seasonalanimetracker.app.common.ui.media

import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter

interface OnCharactersExpandClickListener {
    fun showMoreCharacters(characters: List<MediaCharacter>)
}