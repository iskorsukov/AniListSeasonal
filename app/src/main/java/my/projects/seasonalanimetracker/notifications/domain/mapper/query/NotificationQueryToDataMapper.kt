package my.projects.seasonalanimetracker.notifications.domain.mapper.query

import my.projects.seasonalanimetracker.NotificationsQuery
import my.projects.seasonalanimetracker.app.common.data.characters.Character
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.characters.VoiceActor
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.common.data.staff.Staff
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import javax.inject.Inject

class NotificationQueryToDataMapper @Inject constructor() {

    fun map(queryNotification: NotificationsQuery.AsAiringNotification): NotificationMediaItem {
        return NotificationMediaItem(
            queryNotification.id.toLong(),
            queryNotification.createdAt!!.toLong() * 1000,
            queryNotification.episode,
            mapMedia(queryNotification.media!!)
        )
    }

    fun mapMedia(media: NotificationsQuery.Media): Media {
        return media.fragments.mediaFragment.run {
            Media(
                id.toLong(),
                type!!.name,
                format?.name ?: "",
                title?.romaji,
                title?.native_,
                title?.english,
                season.toString(),
                seasonYear,
                description,
                coverImage?.large,
                bannerImage,
                episodes,
                genres?.run {
                    if (isEmpty()) {
                        ""
                    } else {
                        reduceRight { first, second -> "$first, $second" }
                    }
                },
                averageScore?.toDouble(),
                meanScore?.toDouble(),
                mapCharacters(media.characters!!),
                mapStaff(media.staff!!),
                emptyList(),
                siteUrl!!,
                mediaListEntry?.status?.name
            )
        }
    }

    fun mapCharacters(characters: NotificationsQuery.Characters): List<MediaCharacter> {
        return characters.fragments.characterFragment.edges?.mapNotNull { edge ->
            if (edge == null) {
                null
            } else {
                MediaCharacter(
                    edge.id!!.toLong(),
                    edge.node!!.run {
                        Character(
                            id.toLong(),
                            name?.full ?: "",
                            image?.medium
                        )
                    },
                    edge.voiceActors?.run {
                        if (isEmpty()) {
                            null
                        } else {
                            first()!!.run {
                                VoiceActor(
                                    id.toLong(),
                                    name?.full ?: "",
                                    language?.name ?: "",
                                    image?.medium
                                )
                            }
                        }
                    }
                )
            }
        }?.sortedBy { it.id } ?: emptyList()
    }

    fun mapStaff(staff: NotificationsQuery.Staff): List<MediaStaff> {
        return staff.fragments.staffFragment.edges?.mapNotNull { edge ->
            if (edge == null) {
                null
            } else {
                MediaStaff(
                    edge.id!!.toLong(),
                    edge.node!!.run {
                        Staff(
                            id.toLong(),
                            name?.full ?: "",
                            image?.medium
                        )
                    },
                    edge.role ?: ""
                )
            }
        }?.sortedBy { it.id } ?: emptyList()
    }
}