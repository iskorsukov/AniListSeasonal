package my.projects.seasonalanimetracker.notifications.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import my.projects.seasonalanimetracker.app.common.ui.media.OnMediaItemClickListener
import my.projects.seasonalanimetracker.databinding.ItemNotificationBinding
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem

class NotificationsRecyclerViewPagedAdapter(private val mediaItemClickListener: OnMediaItemClickListener)
    : PagedListAdapter<NotificationMediaItem, NotificationItemViewHolder>(NotificationDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                getItem(position)?.let {
                    mediaItemClickListener.onClickMediaItem(it.media)
                }
            }
        }
    }

}

class NotificationDiffUtilCallback: DiffUtil.ItemCallback<NotificationMediaItem>() {

    override fun areItemsTheSame(
        oldItem: NotificationMediaItem,
        newItem: NotificationMediaItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: NotificationMediaItem,
        newItem: NotificationMediaItem
    ): Boolean {
        return oldItem == newItem
    }

}

