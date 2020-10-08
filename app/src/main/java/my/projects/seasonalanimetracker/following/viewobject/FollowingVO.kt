package my.projects.seasonalanimetracker.following.viewobject

import my.projects.seasonalanimetracker.following.data.FollowingMediaItem

interface IFollowingVO {
    fun getItems(): List<FollowingMediaItem>
    fun getSeason(): Pair<String, Int>
}

class FollowingVO(private val items: List<FollowingMediaItem>, private val season: Pair<String, Int>): IFollowingVO {
    override fun getItems(): List<FollowingMediaItem> {
        return items
    }

    override fun getSeason(): Pair<String, Int> {
        return season
    }
}