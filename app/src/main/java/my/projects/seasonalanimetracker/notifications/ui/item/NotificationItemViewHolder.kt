package my.projects.seasonalanimetracker.notifications.ui.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_notification.view.*
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import java.text.DateFormat
import java.util.*

class NotificationItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: NotificationMediaItem?) {

        if (item == null) return // TODO add a placeholder

        if (item.media.coverImageUrl != null) {
            Glide.with(itemView).load(item.media.coverImageUrl).into(itemView.image)
        }
        itemView.title.text = item.media.titleEnglish ?: item.media.titleRomaji ?: item.media.titleNative
        itemView.aired.text = "Ep ${item.episode} just aired"

        val formattedTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date(item.createdAt))
        itemView.createdAt.text = formattedTime
    }
}