package my.projects.seasonalanimetracker.notifications.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.databinding.ItemNotificationBinding
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem

class NotificationsRecyclerViewAdapter(private val items: List<NotificationMediaItem>): RecyclerView.Adapter<NotificationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}