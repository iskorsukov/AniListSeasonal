package my.projects.seasonalanimetracker.media.ui

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_media.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment

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
    }
}