package my.projects.seasonalanimetracker.media.ui.item.staff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff

class StaffRecyclerViewAdapter: ListAdapter<MediaStaff, MediaStaffViewHolder>(MediaStaffDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaStaffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent, false)
        return MediaStaffViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaStaffViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MediaStaffDiffUtilCallback: DiffUtil.ItemCallback<MediaStaff>() {
    override fun areItemsTheSame(oldItem: MediaStaff, newItem: MediaStaff): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaStaff, newItem: MediaStaff): Boolean {
        return oldItem.role == newItem.role && oldItem.staff == newItem.staff
    }

}