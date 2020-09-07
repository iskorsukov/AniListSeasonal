package my.projects.seasonalanimetracker.following.ui.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_following.view.*
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

class FollowingItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: FollowingMediaItem) {
        item.media.coverImageUrl?.apply {
            Glide.with(itemView).load(this).into(itemView.image)
        }
        itemView.title.text = item.media.titleEnglish ?: item.media.titleRomaji ?: item.media.titleNative
        itemView.status.text = item.status
    }
}