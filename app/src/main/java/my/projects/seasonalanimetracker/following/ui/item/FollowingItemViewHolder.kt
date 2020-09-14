package my.projects.seasonalanimetracker.following.ui.item

import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.databinding.ItemFollowingBinding
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

class FollowingItemViewHolder(private val binding: ItemFollowingBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FollowingMediaItem) {
        binding.following = item
        binding.executePendingBindings()
    }
}