package my.projects.seasonalanimetracker.following.domain.mapper.db

import my.projects.seasonalanimetracker.app.common.domain.mapper.MediaEntityToDataMapper
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItemEntity
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import javax.inject.Inject

class FollowingEntityToDataMapper @Inject constructor(): MediaEntityToDataMapper() {

    fun map(entity: DBFollowingItemEntity): FollowingMediaItem {
        return FollowingMediaItem(
            entity.followingItem.id,
            entity.followingItem.status,
            mapMedia(entity.mediaEntity)
        )
    }
}