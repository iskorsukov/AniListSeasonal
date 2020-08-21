package my.projects.seasonalanimetracker.schedule.ui.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_schedule.view.*
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class ScheduleItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(item: ScheduleMediaItem) {
        item.media.coverImageUrl?.apply {
            Glide.with(itemView).load(this).into(itemView.image)
        }
        itemView.title.text = item.media.titleEnglish ?: item.media.titleRomaji ?: item.media.titleNative
        itemView.airing.text = getAiringString(item.episode, item.airingAt)
    }

    private fun getAiringString(episode: Int, airingAt: Long): String {
        val airingBuilder = StringBuilder()
        airingBuilder.append("Ep $episode ")

        val formattedTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(airingAt))
        if (airingAt < System.currentTimeMillis()) {
            airingBuilder.append("aired at $formattedTime")
        } else {
            airingBuilder.append("airing at $formattedTime")
        }

        return airingBuilder.toString()
    }
}