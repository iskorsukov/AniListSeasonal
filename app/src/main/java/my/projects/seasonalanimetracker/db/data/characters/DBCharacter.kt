package my.projects.seasonalanimetracker.db.data.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class DBCharacter(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String?
)