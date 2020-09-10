package my.projects.seasonalanimetracker.media.ui.item.staff

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_staff.view.*
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff

class MediaStaffViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: MediaStaff) {
        Glide.with(itemView).load(item.staff.imageUrl).into(itemView.staff_image)
        itemView.staff_name.text = item.staff.name
        itemView.staff_position.text = item.role
    }
}