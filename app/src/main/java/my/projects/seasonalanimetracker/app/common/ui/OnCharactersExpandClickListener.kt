package my.projects.seasonalanimetracker.app.common.ui

import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter

interface OnCharactersExpandClickListener {
    fun showMoreCharacters(characters: List<MediaCharacter>)
}