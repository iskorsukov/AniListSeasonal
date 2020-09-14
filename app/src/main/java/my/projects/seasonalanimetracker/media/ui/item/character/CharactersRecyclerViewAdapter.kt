package my.projects.seasonalanimetracker.media.ui.item.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.databinding.ItemCharacterBinding
import my.projects.seasonalanimetracker.databinding.ItemCharacterExpandedBinding

class CharactersRecyclerViewAdapter:
    ListAdapter<MediaCharacter, MediaCharacterViewHolder>(MediaCharacterDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaCharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaCharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CharactersExtendedRecyclerViewAdapter:
    ListAdapter<MediaCharacter, MediaCharacterExtendedViewHolder>(MediaCharacterDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCharacterExtendedViewHolder {
        val binding = ItemCharacterExpandedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaCharacterExtendedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaCharacterExtendedViewHolder, position: Int) {
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