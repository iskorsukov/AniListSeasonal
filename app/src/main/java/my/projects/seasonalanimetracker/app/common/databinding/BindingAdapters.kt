package my.projects.seasonalanimetracker.app.common.databinding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_media_right_strip.view.*
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.media.ui.MediaFragmentDirections
import my.projects.seasonalanimetracker.media.ui.item.character.CharactersRecyclerViewAdapter
import my.projects.seasonalanimetracker.media.ui.item.character.MediaCharacterViewHolder
import my.projects.seasonalanimetracker.media.ui.item.character.MediaCharactersSerializable
import my.projects.seasonalanimetracker.media.ui.item.staff.MediaStaffViewHolder
import java.text.DateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView).load(imageUrl).into(imageView)
}

@BindingAdapter("charactersList")
fun bindCharactersList(charactersRecycler: RecyclerView, characters: List<MediaCharacter>) {
    val adapter = charactersRecycler.adapter
    if (adapter != null && adapter is ListAdapter<*, *>) {
        val charactersAdapter = adapter as ListAdapter<MediaCharacter, MediaCharacterViewHolder>
        if (characters.size <= 2) {
            charactersAdapter.submitList(characters)
        } else {
            charactersAdapter.submitList(characters.subList(0, 2))
        }
    }
}

@BindingAdapter("staffList")
fun bindStaffList(staffRecycler: RecyclerView, staff: List<MediaStaff>) {
    val adapter = staffRecycler.adapter
    if (adapter != null && adapter is ListAdapter<*, *>) {
        val charactersAdapter = adapter as ListAdapter<MediaStaff, MediaStaffViewHolder>
        if (staff.size <= 2) {
            charactersAdapter.submitList(staff)
        } else {
            charactersAdapter.submitList(staff.subList(0, 2))
        }
    }
}

@BindingAdapter("airingAt", "episode")
fun bindAiringString(view: TextView, airingAt: Long, episode: Int?) {
    val airingBuilder = StringBuilder()

    airingBuilder.append("Ep ")
    if (episode != null) {
        airingBuilder.append("$episode ")
    }

    val formattedTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(airingAt))
    if (airingAt < System.currentTimeMillis()) {
        airingBuilder.append("aired at $formattedTime")
    } else {
        airingBuilder.append("airing at $formattedTime")
    }

    view.text = airingBuilder.toString()
}

@BindingAdapter("formattedTime")
fun bindFormattedTime(view: TextView, time: Long) {
    val formattedTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(time))
    view.text = formattedTime
}