package my.projects.seasonalanimetracker.media.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_media.*
import kotlinx.android.synthetic.main.fragment_media_right_strip.view.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.databinding.FragmentMediaBinding
import my.projects.seasonalanimetracker.media.ui.item.character.CharactersRecyclerViewAdapter
import my.projects.seasonalanimetracker.media.ui.item.character.MediaCharactersSerializable
import my.projects.seasonalanimetracker.media.ui.item.staff.MediaStaffSerializable
import my.projects.seasonalanimetracker.media.ui.item.staff.StaffRecyclerViewAdapter

class MediaFragment: BaseFragment() {

    private class NoScrollLinearLayoutManager(context: Context): LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return false
        }

        override fun canScrollHorizontally(): Boolean {
            return false
        }
    }

    private val charactersAdapter = CharactersRecyclerViewAdapter()
    private val staffAdapter = StaffRecyclerViewAdapter()

    private val args by navArgs<MediaFragmentArgs>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_media
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val media = args.media
        FragmentMediaBinding.bind(view).also {
            it.media = media
        }

        if (media.bannerImageUrl != null) {
            Glide.with(view).load(media.bannerImageUrl).into(banner)
        } else {
            banner.visibility = View.GONE
        }

        Glide.with(view).load(media.coverImageUrl).into(cover)

        if (media.character.isEmpty()) {
            view.characters_label.visibility = View.GONE
            view.characters_recycler.visibility = View.GONE
            view.characters_show_more.visibility = View.GONE
        } else {
            view.characters_recycler.layoutManager = NoScrollLinearLayoutManager(requireContext())
            view.characters_recycler.adapter = charactersAdapter
            if (media.character.size <= 2) {
                charactersAdapter.submitList(media.character)
                view.characters_show_more.visibility = View.GONE
            } else {
                charactersAdapter.submitList(media.character.subList(0, 2))
                view.characters_show_more.setOnClickListener {
                    findNavController().navigate(MediaFragmentDirections.actionMediaFragmentToCharactersFragment(
                        MediaCharactersSerializable(ArrayList(media.character))
                    ))
                }
            }
        }

        if (media.staff.isEmpty()) {
            view.staff_label.visibility = View.GONE
            view.staff_recycler.visibility = View.GONE
            view.staff_show_more.visibility = View.GONE
        } else {
            view.staff_recycler.layoutManager = NoScrollLinearLayoutManager(requireContext())
            view.staff_recycler.adapter = staffAdapter
            if (media.staff.size <= 2) {
                staffAdapter.submitList(media.staff)
                view.staff_show_more.visibility = View.GONE
            } else {
                staffAdapter.submitList(media.staff.subList(0, 2))
                view.staff_show_more.setOnClickListener {
                    findNavController().navigate(MediaFragmentDirections.actionMediaFragmentToStaffFragment(
                        MediaStaffSerializable(ArrayList(media.staff))
                    ))
                }
            }
        }
    }
}