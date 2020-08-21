package my.projects.seasonalanimetracker.schedule.domain.mapper.db

import my.projects.seasonalanimetracker.db.data.characters.DBCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacterEntity
import my.projects.seasonalanimetracker.db.data.characters.DBVoiceActor
import my.projects.seasonalanimetracker.db.data.media.DBMediaEntity
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaffEntity
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudioEntity
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.common.data.staff.Staff
import my.projects.seasonalanimetracker.app.common.data.studios.MediaStudio
import my.projects.seasonalanimetracker.app.common.data.studios.Studio
import javax.inject.Inject

class ScheduleEntityToDataMapper @Inject constructor() {

    fun map(entity: DBScheduleItemEntity): ScheduleMediaItem {
        return ScheduleMediaItem(
            id = entity.scheduleItem.id,
            airingAt = entity.scheduleItem.airingAt,
            episode = entity.scheduleItem.episode,
            media = mapMedia(entity.mediaEntity)
        )
    }

    fun mapMedia(entity: DBMediaEntity): Media {
        return Media(
            id = entity.media.id,
            type = entity.media.type,
            format = entity.media.format,
            titleRomaji = entity.media.title?.romaji,
            titleEnglish = entity.media.title?.english,
            titleNative = entity.media.title?.nativeT,
            description = entity.media.description,
            coverImageUrl = entity.media.coverImageUrl,
            bannerImageUrl = entity.media.bannerImageUrl,
            episodes = entity.media.episodes,
            genres = entity.media.genres,
            averageScore = entity.media.averageScore,
            meanScore = entity.media.meanScore,
            character = mapCharacters(entity.characterEntities),
            studios = mapStudios(entity.studioEntities),
            staff = mapStaff(entity.staffEntities),
            siteUrl = entity.media.siteUrl
        )
    }

    fun mapCharacters(characterEntities: List<DBMediaCharacterEntity>): List<MediaCharacter> {
        return characterEntities.map { entity ->
            MediaCharacter(
                entity.mediaCharacter.id,
                mapCharacter(entity.character),
                mapVoiceActor(entity.voiceActor)
            )
        }
    }

    fun mapCharacter(characterEntity: DBCharacter): my.projects.seasonalanimetracker.app.common.data.characters.Character {
        return my.projects.seasonalanimetracker.app.common.data.characters.Character(
            characterEntity.id,
            characterEntity.name,
            characterEntity.imageUrl
        )
    }

    fun mapVoiceActor(voiceActorEntity: DBVoiceActor?): my.projects.seasonalanimetracker.app.common.data.characters.VoiceActor? {
        return voiceActorEntity?.let {
            my.projects.seasonalanimetracker.app.common.data.characters.VoiceActor(
                voiceActorEntity.id,
                voiceActorEntity.name,
                voiceActorEntity.language,
                voiceActorEntity.imageUrl
            )
        }
    }

    fun mapStudios(studioEntities: List<DBMediaStudioEntity>): List<MediaStudio> {
        return studioEntities.map { entity ->
            MediaStudio(
                entity.mediaStudio.id,
                Studio(
                    entity.studio.id,
                    entity.studio.name
                )
            )
        }
    }

    fun mapStaff(staffEntities: List<DBMediaStaffEntity>): List<MediaStaff> {
        return staffEntities.map { entity ->
            MediaStaff(
                entity.mediaStaff.id,
                Staff(
                    entity.staff.id,
                    entity.staff.name,
                    entity.staff.imageUrl
                ),
                entity.mediaStaff.role
            )
        }
    }

}