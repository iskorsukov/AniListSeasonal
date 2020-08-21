package my.projects.seasonalanimetracker.db.data.staff

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "show_staff",
    foreignKeys = [
        ForeignKey(
            entity = DBStaff::class,
            parentColumns = ["id"],
            childColumns = ["staffId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DBMediaStaff(
    @PrimaryKey val id: Long,
    val mediaId: Long,
    val staffId: Long,
    val role: String
)