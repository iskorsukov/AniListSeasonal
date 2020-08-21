package my.projects.seasonalanimetracker.db.data.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voice_actors")
data class DBVoiceActor(
    @PrimaryKey val id: Long,
    val name: String,
    val language: String,
    val imageUrl: String?
)