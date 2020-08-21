package my.projects.seasonalanimetracker.db.data.staff

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
data class DBStaff(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String?
)