package my.projects.seasonalanimetracker.following.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

class FollowingRecyclerViewAdapter(private val mediaItemClickListener: OnMediaItemClickListener) : ListAdapter<FollowingMediaItem, FollowingItemViewHolder>(FollowingDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingItemViewHolder {
        return FollowingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_following, parent, false))
    }

    override fun onBindViewHolder(holder: FollowingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
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