package my.projects.seasonalanimetracker.db.data.following

import androidx.room.Embedded
import androidx.room.Relation
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.media.DBMediaEntity

data class DBFollowingItemEntity(
    @Embedded
    val followingItem: DBFollowingItem,
    @Relation(
        entity = DBMedia::class,
        parentColumn = "mediaId",
        entityColumn = "id"
    )
    val mediaEntity: DBMediaEntity
)