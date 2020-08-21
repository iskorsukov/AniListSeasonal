package my.projects.seasonalanimetracker.db.data.characters

import androidx.room.Embedded
import androidx.room.Relation

data class DBMediaCharacterEntity(
    @Embedded val mediaCharacter: DBMediaCharacter,
    @Relation(
        entity = DBCharacter::class,
        parentColumn = "characterId",
        entityColumn = "id"
    )
    val character: DBCharacter,
    @Relation(
        entity = DBVoiceActor::class,
        parentColumn = "voiceActorId",
        entityColumn = "id"
    )
    val voiceActor: DBVoiceActor?
)