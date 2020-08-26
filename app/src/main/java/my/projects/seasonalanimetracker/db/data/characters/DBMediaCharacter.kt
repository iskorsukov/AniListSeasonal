package my.projects.seasonalanimetracker.db.data.characters

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "show_character",
    foreignKeys = [
        ForeignKey(
            entity = DBCharacter::class,
            parentColumns = ["id"],
            childColumns = ["characterId"]
        ),
        ForeignKey(
            entity = DBVoiceActor::class,
            parentColumns = ["id"],
            childColumns = ["voiceActorId"]
        )
    ]
)
data class DBMediaCharacter(
    @PrimaryKey val id: Long,
    val mediaId: Long,
    val characterId: Long,
    val voiceActorId: Long?
)