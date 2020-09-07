package my.projects.seasonalanimetracker.following.domain.mapper.db

import my.projects.seasonalanimetracker.app.common.domain.mapper.MediaDataToEntityMapper
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItem
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItemEntity
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import javax.inject.Inject

class FollowingDataToEntityMapper @Inject constructor(): MediaDataToEntityMapper() {

    fun map(item: FollowingMediaItem): DBFollowingItemEntity {
        return DBFollowingItemEntity(
            DBFollowingItem(
                item.id,
                item.media.id,
                item.status
            ),
            mapMediaEntity(item.media)
        )
    }
}