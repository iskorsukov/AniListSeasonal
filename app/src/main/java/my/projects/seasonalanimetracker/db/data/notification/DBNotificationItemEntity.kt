package my.projects.seasonalanimetracker.db.data.notification

import androidx.room.Embedded
import androidx.room.Relation
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.media.DBMediaEntity

data class DBNotificationItemEntity(
    @Embedded
    val notificationItem: DBNotificationItem,
    @Relation(
        entity = DBMedia::class,
        parentColumn = "mediaId",
        entityColumn = "id"
    )
    val mediaEntity: DBMediaEntity
)