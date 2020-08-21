package my.projects.seasonalanimetracker.db.data.media

import androidx.room.Embedded
import androidx.room.Relation
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacterEntity
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaff
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaffEntity
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudio
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudioEntity

data class DBMediaEntity (
    @Embedded val media: DBMedia,
    @Relation(
        entity = DBMediaCharacter::class,
        parentColumn = "id",
        entityColumn = "mediaId"
    )
    val characterEntities: List<DBMediaCharacterEntity>,
    @Relation(
        entity = DBMediaStudio::class,
        parentColumn = "id",
        entityColumn = "mediaId"
    )
    val studioEntities: List<DBMediaStudioEntity>,
    @Relation(
        entity = DBMediaStaff::class,
        parentColumn = "id",
        entityColumn = "mediaId"
    )
    val staffEntities: List<DBMediaStaffEntity>
)