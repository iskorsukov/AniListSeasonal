package my.projects.seasonalanimetracker.media.ui.item.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter

class CharactersRecyclerViewAdapter(private val expanded: Boolean = false):
    ListAdapter<MediaCharacter, MediaCharacterViewHolder>(MediaCharacterDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCharacterViewHolder {
        val layoutId = if (expanded) R.layout.item_character_expanded else R.layout.item_character
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MediaCharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaCharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MediaCharacterDiffUtilCallback: DiffUtil.ItemCallback<MediaCharacter>() {
    override fun areItemsTheSame(oldItem: MediaCharacter, newItem: MediaCharacter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaCharacter, newItem: MediaCharacter): Boolean {
        val sameVA = oldItem.voiceActor != null && oldItem.voiceActor == newItem.voiceActor ||
                newItem.voiceActor != null && newItem.voiceActor == oldItem.voiceActor ||
                oldItem.voiceActor == null && newItem.voiceActor == null

        return oldItem.character == newItem.character && sameVA
    }

}