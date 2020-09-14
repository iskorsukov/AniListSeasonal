package my.projects.seasonalanimetracker.media.ui.item.staff

import androidx.recyclerview.widget.RecyclerView
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.databinding.ItemStaffBinding

class MediaStaffViewHolder(private val binding: ItemStaffBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaStaff) {
        binding.staff = item
        binding.executePendingBindings()
    }
}