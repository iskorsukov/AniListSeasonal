package my.projects.seasonalanimetracker.notifications.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import timber.log.Timber

class NotificationsRecyclerViewPagedAdapter
    : PagedListAdapter<NotificationMediaItem, NotificationItemViewHolder>(NotificationDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        return NotificationItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(getItem(position))
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

