package my.projects.seasonalanimetracker.db.data.schedule

import androidx.room.Embedded
import androidx.room.Relation
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.media.DBMediaEntity

data class DBScheduleItemEntity(
    @Embedded val scheduleItem: DBScheduleItem,
    @Relation(
        entity = DBMedia::class,
        parentColumn = "mediaId",
        entityColumn = "id"
    )
    val mediaEntity: DBMediaEntity
)