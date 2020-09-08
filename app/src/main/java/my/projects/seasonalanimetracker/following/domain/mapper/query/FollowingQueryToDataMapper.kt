package my.projects.seasonalanimetracker.following.domain.mapper.query

import my.projects.seasonalanimetracker.FollowingPageQuery
import my.projects.seasonalanimetracker.app.common.data.characters.Character
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.characters.VoiceActor
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.common.data.staff.Staff
import my.projects.seasonalanimetracker.app.common.data.studios.MediaStudio
import my.projects.seasonalanimetracker.app.common.data.studios.Studio
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import javax.inject.Inject

class FollowingQueryToDataMapper @Inject constructor() {

    fun map(queryFollowing: FollowingPageQuery.MediaList): FollowingMediaItem {
        return FollowingMediaItem(
            queryFollowing.id.toLong(),
            queryFollowing.status.toString(),
            mapMedia(queryFollowing.media!!)
        )
    }

    fun mapMedia(media: FollowingPageQuery.Media): Media {
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
                mapStudios(media.studios!!),
                siteUrl!!
            )
        }
    }

    fun mapCharacters(characters: FollowingPageQuery.Characters): List<MediaCharacter> {
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
        } ?: emptyList()
    }

    fun mapStaff(staff: FollowingPageQuery.Staff): List<MediaStaff> {
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
        } ?: emptyList()
    }

    fun mapStudios(studios: FollowingPageQuery.Studios): List<MediaStudio> {
        return studios.fragments.studioFragment.nodes?.mapNotNull { node ->
            if (node == null) {
                null
            } else {
                MediaStudio(
                    node.id.toLong(),
                    Studio(
                        node.id.toLong(),
                        node.name
                    )
                )
            }
        } ?: emptyList()
    }
}