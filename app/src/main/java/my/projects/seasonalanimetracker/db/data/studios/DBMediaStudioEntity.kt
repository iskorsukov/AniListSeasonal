package my.projects.seasonalanimetracker.db.data.studios

import androidx.room.Embedded
import androidx.room.Relation

data class DBMediaStudioEntity(
    @Embedded val mediaStudio: DBMediaStudio,
    @Relation(
        entity = DBStudio::class,
        parentColumn = "studioId",
        entityColumn = "id"
    )
    val studio: DBStudio
)