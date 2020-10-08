package my.projects.seasonalanimetracker.app.common.domain.mapper

import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.common.data.studios.MediaStudio
import my.projects.seasonalanimetracker.db.data.characters.DBCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacterEntity
import my.projects.seasonalanimetracker.db.data.characters.DBVoiceActor
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.media.DBMediaEntity
import my.projects.seasonalanimetracker.db.data.media.MediaTitle
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaff
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaffEntity
import my.projects.seasonalanimetracker.db.data.staff.DBStaff
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudio
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudioEntity
import my.projects.seasonalanimetracker.db.data.studios.DBStudio

abstract class MediaDataToEntityMapper {

    protected fun mapMediaEntity(media: Media): DBMediaEntity {
        return DBMediaEntity(
            DBMedia(
                media.id,
                media.type,
                media.format,
                MediaTitle(
                    media.titleRomaji,
                    media.titleNative,
                    media.titleEnglish
                ),
                media.season,
                media.seasonYear,
                media.description,
                media.coverImageUrl,
                media.bannerImageUrl,
                media.episodes,
                media.genres,
                media.averageScore,
                media.meanScore,
                media.siteUrl,
                media.userStatus
            ),
            mapMediaCharacters(media.character, media.id),
            mapMediaStudios(media.studios, media.id),
            mapMediaStaff(media.staff, media.id)
        )
    }

    private fun mapMediaCharacters(characters: List<MediaCharacter>, mediaId: Long): List<DBMediaCharacterEntity> {
        return characters.map {
            DBMediaCharacterEntity(
                DBMediaCharacter(
                    it.id,
                    mediaId,
                    it.character.id,
                    it.voiceActor?.id
                ),
                DBCharacter(
                    it.character.id,
                    it.character.name,
                    it.character.imageUrl
                ),
                it.voiceActor?.let { voiceActor ->
                    DBVoiceActor(
                        voiceActor.id,
                        voiceActor.name,
                        voiceActor.language,
                        voiceActor.imageUrl
                    )
                }
            )
        }
    }

    private fun mapMediaStaff(staff: List<MediaStaff>, mediaId: Long): List<DBMediaStaffEntity> {
        return staff.map {
            DBMediaStaffEntity(
                DBMediaStaff(
                    it.id,
                    mediaId,
                    it.staff.id,
                    it.role
                ),
                DBStaff(
                    it.staff.id,
                    it.staff.name,
                    it.staff.imageUrl
                )
            )
        }
    }

    private fun mapMediaStudios(studios: List<MediaStudio>, mediaId: Long): List<DBMediaStudioEntity> {
        return studios.map {
            DBMediaStudioEntity(
                DBMediaStudio(
                    it.id,
                    mediaId,
                    it.studio.id
                ),
                DBStudio(
                    it.studio.id,
                    it.studio.name
                )
            )
        }
    }
}