package my.projects.seasonalanimetracker.app.common.ui.media.status

import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.following.data.MediaListAction
import my.projects.seasonalanimetracker.type.MediaListStatus

interface SelectMediaListActionListener {
    fun onMediaStatusSelected(item: Media, action: MediaListAction)
}