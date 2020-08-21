package my.projects.seasonalanimetracker.schedule.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.time.DayOfWeek

class ScheduleRecyclerViewAdapter: ListAdapter<ScheduleMediaItem, ScheduleItemViewHolder>(ScheduleDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        holder.bind(getItem(position))

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