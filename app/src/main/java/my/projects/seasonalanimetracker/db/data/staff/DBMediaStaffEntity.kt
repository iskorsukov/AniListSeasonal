package my.projects.seasonalanimetracker.db.data.staff

import androidx.room.Embedded
import androidx.room.Relation

data class DBMediaStaffEntity(
    @Embedded val mediaStaff: DBMediaStaff,
    @Relation(
        entity = DBStaff::class,
        parentColumn = "staffId",
        entityColumn = "id"
    )
    val staff: DBStaff
)