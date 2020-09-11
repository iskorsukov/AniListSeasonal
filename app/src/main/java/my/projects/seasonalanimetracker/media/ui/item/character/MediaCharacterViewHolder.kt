package my.projects.seasonalanimetracker.media.ui.item.character

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import kotlinx.android.synthetic.main.item_character.view.*
import kotlinx.android.synthetic.main.item_character.view.character_image
import kotlinx.android.synthetic.main.item_character.view.character_name
import kotlinx.android.synthetic.main.item_character_expanded.view.*

class MediaCharacterViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: MediaCharacter) {
        with(itemView) {
            Glide.with(this).load(item.character.imageUrl).into(character_image)
            itemView.character_name.text = item.character.name

            if (item.voiceActor != null && va_name != null) {
                Glide.with(this).load(item.voiceActor.imageUrl).into(va_image)
                va_name.text = item.voiceActor.name
                va_language.text = item.voiceActor.language
            }
        }
    }
}