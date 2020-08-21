package my.projects.seasonalanimetracker.db.data.studios

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studios")
data class DBStudio(
    @PrimaryKey val id: Long,
    val name: String
)