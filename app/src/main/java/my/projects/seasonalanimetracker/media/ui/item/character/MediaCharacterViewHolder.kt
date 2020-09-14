package my.projects.seasonalanimetracker.media.ui.item.character

import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.databinding.ItemCharacterBinding
import my.projects.seasonalanimetracker.databinding.ItemCharacterExpandedBinding

class MediaCharacterViewHolder(private val binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaCharacter) {
        binding.character = item
        binding.executePendingBindings()
    }
}

class MediaCharacterExtendedViewHolder(private val binding: ItemCharacterExpandedBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MediaCharacter) {
        binding.character = item
        binding.executePendingBindings()
    }
}