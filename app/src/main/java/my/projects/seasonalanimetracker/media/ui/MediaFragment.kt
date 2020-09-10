package my.projects.seasonalanimetracker.media.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_media.*
import kotlinx.android.synthetic.main.fragment_media_left_strip.*
import kotlinx.android.synthetic.main.fragment_media_right_strip.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.ui.OnCharactersExpandClickListener
import my.projects.seasonalanimetracker.app.common.ui.OnStaffExpandClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.media.ui.item.character.CharactersRecyclerViewAdapter
import my.projects.seasonalanimetracker.media.ui.item.staff.StaffRecyclerViewAdapter
import timber.log.Timber

class MediaFragment: BaseFragment() {

    companion object {
        fun newInstance(media: Media): MediaFragment {
            return MediaFragment().also {
                it.arguments = Bundle().also {
                    it.putSerializable("media", media)
                }
            }
        }
    }

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

    override fun getLayoutId(): Int {
        return R.layout.fragment_media
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val media = requireArguments().getSerializable("media") as Media

        if (media.bannerImageUrl != null) {
            Glide.with(view).load(media.bannerImageUrl).into(banner)
        } else {
            banner.visibility = View.GONE
        }

        Glide.with(view).load(media.coverImageUrl).into(cover)
        title.text = media.titleEnglish ?: media.titleRomaji ?: media.titleNative
        description.text = media.description

        if (media.season == null && media.seasonYear == null) {
            season_label.visibility = View.GONE
            season.visibility = View.GONE
        } else {
            season.text = "${media.season} ${media.seasonYear}"
        }

        if (media.episodes == null) {
            episodes_label.visibility = View.GONE
            episodes.visibility = View.GONE
        } else {
            episodes.text = media.episodes.toString()
        }

        if (media.genres == null) {
            genres_label.visibility = View.GONE
            genres.visibility = View.GONE
        } else {
            genres.text = media.genres
        }

        if (media.averageScore == null) {
            avg_score_label.visibility = View.GONE
            avg_score.visibility = View.GONE
        } else {
            avg_score.text = media.averageScore.toString()
        }

        if (media.meanScore == null) {
            mean_score_label.visibility = View.GONE
            mean_score.visibility = View.GONE
        } else {
            mean_score.text = media.meanScore.toString()
        }

        if (media.studios.isEmpty()) {
            studios_label.visibility = View.GONE
            studios.visibility = View.GONE
        } else {
            studios.text = media.studios.joinToString(separator = ", ") { studio -> studio.studio.name }
        }

        if (media.character.isEmpty()) {
            characters_label.visibility = View.GONE
            characters_recycler.visibility = View.GONE
            characters_show_more.visibility = View.GONE
        } else {
            characters_recycler.layoutManager = NoScrollLinearLayoutManager(requireContext())
            characters_recycler.adapter = charactersAdapter
            if (media.character.size <= 2) {
                charactersAdapter.submitList(media.character)
                characters_show_more.visibility = View.GONE
            } else {
                charactersAdapter.submitList(media.character.subList(0, 2))
                characters_show_more.setOnClickListener {
                    (requireActivity() as OnCharactersExpandClickListener).showMoreCharacters(media.character)
                }
            }
        }

        if (media.staff.isEmpty()) {
            staff_label.visibility = View.GONE
            staff_recycler.visibility = View.GONE
            staff_show_more.visibility = View.GONE
        } else {
            staff_recycler.layoutManager = NoScrollLinearLayoutManager(requireContext())
            staff_recycler.adapter = staffAdapter
            if (media.staff.size <= 2) {
                staffAdapter.submitList(media.staff)
                staff_show_more.visibility = View.GONE
            } else {
                staffAdapter.submitList(media.staff.subList(0, 2))
                staff_show_more.setOnClickListener {
                    (requireActivity() as OnStaffExpandClickListener).showMoreStaff(media.staff)
                }
            }
        }

        Timber.i("${media.titleNative} - ${media.titleRomaji} - ${media.titleEnglish}")
        Timber.i("${media.description}")
        Timber.i("${media.episodes}")
        Timber.i("${media.genres}")
    }
}