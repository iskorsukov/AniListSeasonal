package my.projects.seasonalanimetracker.schedule.ui.item

import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.databinding.ItemScheduleBinding
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem

class ScheduleItemViewHolder(private val binding: ItemScheduleBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ScheduleMediaItem, isAuth: Boolean) {
        binding.schedule = item
        binding.isAuth = isAuth
        binding.executePendingBindings()
    }
}