package my.projects.seasonalanimetracker.schedule.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.item_schedule.view.*
import my.projects.seasonalanimetracker.app.common.ui.media.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.common.ui.media.status.OnModifyMediaStatusClickListener
import my.projects.seasonalanimetracker.databinding.ItemScheduleBinding
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem

class ScheduleRecyclerViewAdapter(
    private val mediaItemClickListener: OnMediaItemClickListener,
    private val mediaStatusClickListener: OnModifyMediaStatusClickListener,
    private val isAuth: Boolean
): ListAdapter<ScheduleMediaItem, ScheduleItemViewHolder>(ScheduleDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        holder.bind(getItem(position), isAuth)
        holder.itemView.setOnClickListener {
            mediaItemClickListener.onClickMediaItem(getItem(position).media)
        }
        holder.itemView.status_action.setOnClickListener {
            mediaStatusClickListener.onModifyMediaStatusClick(getItem(position).media)
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