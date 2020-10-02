package my.projects.seasonalanimetracker.following.ui.item

import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

interface OnRemoveFollowingClickListener {
    fun onRemoveClick(item: FollowingMediaItem)
}