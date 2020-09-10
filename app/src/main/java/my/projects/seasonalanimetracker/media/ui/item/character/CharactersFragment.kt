package my.projects.seasonalanimetracker.media.ui.item.character

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_characters.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment

class CharactersFragment: BaseFragment() {

    companion object {
        fun newInstance(characters: List<MediaCharacter>): CharactersFragment {
            return CharactersFragment().also {
                it.arguments = Bundle().also {
                    it.putSerializable("characters", ArrayList(characters))
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_characters
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characters_recycler.layoutManager = LinearLayoutManager(context)
        characters_recycler.adapter = CharactersRecyclerViewAdapter(true).also { adapter ->
            (arguments?.getSerializable("characters") as List<MediaCharacter>?)?.let {
                adapter.submitList(it)
            }
        }
    }
}