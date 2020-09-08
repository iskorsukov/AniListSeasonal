package my.projects.seasonalanimetracker.following.viewobject

import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

interface IFollowingVO {
    fun getItems(): List<FollowingMediaItem>
}

class FollowingVO(private val items: List<FollowingMediaItem>): IFollowingVO {
    override fun getItems(): List<FollowingMediaItem> {
        return items
    }
}