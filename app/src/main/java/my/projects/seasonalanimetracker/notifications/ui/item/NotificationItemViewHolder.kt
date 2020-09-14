package my.projects.seasonalanimetracker.notifications.ui.item

import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.databinding.ItemNotificationBinding
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem

class NotificationItemViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NotificationMediaItem) {
        binding.notification = item
        binding.executePendingBindings()
    }
}