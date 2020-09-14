package my.projects.seasonalanimetracker.media.ui.item.character

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_characters.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment

class CharactersFragment: BaseFragment() {

    private val args by navArgs<CharactersFragmentArgs>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_characters
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characters = args.charactersList.character

        characters_recycler.layoutManager = LinearLayoutManager(context)
        characters_recycler.adapter = CharactersExtendedRecyclerViewAdapter().also { adapter ->
            adapter.submitList(characters)
        }
    }
}