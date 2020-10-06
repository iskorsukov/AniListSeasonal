package my.projects.seasonalanimetracker.app.common.ui.media

import my.projects.seasonalanimetracker.app.common.data.media.Media

interface OnMediaItemClickListener {
    fun onClickMediaItem(media: Media)
}