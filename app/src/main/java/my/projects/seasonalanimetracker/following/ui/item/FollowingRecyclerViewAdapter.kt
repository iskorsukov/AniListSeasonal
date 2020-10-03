package my.projects.seasonalanimetracker.following.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.item_following.view.*
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.databinding.ItemFollowingBinding
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

class FollowingRecyclerViewAdapter(
    private val mediaItemClickListener: OnMediaItemClickListener,
    private val modifyFollowingClickListener: OnModifyFollowingClickListener
) : ListAdapter<FollowingMediaItem, FollowingItemViewHolder>(FollowingDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingItemViewHolder {
        val binding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.modify.setOnClickListener {
            modifyFollowingClickListener.onModifyClick(getItem(position))
        }
        holder.itemView.setOnClickListener {
            mediaItemClickListener.onClickMediaItem(getItem(position).media)
        }
    }
}

class FollowingDiffUtilCallback: DiffUtil.ItemCallback<FollowingMediaItem>() {
    override fun areItemsTheSame(
        oldItem: FollowingMediaItem,
        newItem: FollowingMediaItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FollowingMediaItem,
        newItem: FollowingMediaItem
    ): Boolean {
        return oldItem.status == newItem.status && oldItem.media == newItem.media
    }

}