package my.projects.seasonalanimetracker.notifications.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem

class NotificationsRecyclerViewAdapter(private val items: List<NotificationMediaItem>): RecyclerView.Adapter<NotificationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}