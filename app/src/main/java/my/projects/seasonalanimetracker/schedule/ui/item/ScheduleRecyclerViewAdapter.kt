package my.projects.seasonalanimetracker.schedule.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.databinding.ItemScheduleBinding
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.time.DayOfWeek

class ScheduleRecyclerViewAdapter(private val mediaItemClickListener: OnMediaItemClickListener): ListAdapter<ScheduleMediaItem, ScheduleItemViewHolder>(ScheduleDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            mediaItemClickListener.onClickMediaItem(getItem(position).media)
        }
    }
}

class ScheduleDiffUtilCallback: DiffUtil.ItemCallback<ScheduleMediaItem>() {
    override fun areItemsTheSame(oldItem: ScheduleMediaItem, newItem: ScheduleMediaItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ScheduleMediaItem,
        newItem: ScheduleMediaItem
    ): Boolean {
        return oldItem == newItem
    }

}