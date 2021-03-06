package my.projects.seasonalanimetracker.db

import my.projects.seasonalanimetracker.db.data.characters.DBCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacterEntity
import my.projects.seasonalanimetracker.db.data.characters.DBVoiceActor
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItem
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItemEntity
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.media.DBMediaEntity
import my.projects.seasonalanimetracker.db.data.media.MediaTitle
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItem
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItemEntity
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItem
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaff
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaffEntity
import my.projects.seasonalanimetracker.db.data.staff.DBStaff
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudio
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudioEntity
import my.projects.seasonalanimetracker.db.data.studios.DBStudio
import java.util.*

class DBTestDataProvider {

    val characters: List<DBCharacter> = listOf(
        DBCharacter(
            1L,
            "First character",
            null
        ),
        DBCharacter(
            2L,
            "Second character",
            null
        ),
        DBCharacter(
            3L,
            "Third character",
            null
        )
    )
    val voiceActors: List<DBVoiceActor> = listOf(
        DBVoiceActor(
            1L,
            "First VA",
            "japanese",
            null
        ),
        DBVoiceActor(
            2L,
            "Second VA",
            "english",
            null
        ),
        DBVoiceActor(
            3L,
            "Third VA",
            "japanese",
            null
        )
    )
    val staff: List<DBStaff> = listOf(
        DBStaff(
            1L,
            "First staff",
            null
        ),
        DBStaff(
            2L,
            "Second staff",
            null
        ),
        DBStaff(
            3L,
            "Third staff",
            null
        )
    )
    val media: List<DBMedia> = listOf(
        DBMedia(
            1L,
            "anime",
            "tv",
            MediaTitle( null, null, "First"),
            "SUMMER",
            2020,
            "First anime",
            null,
            null,
            18,
            "scifi, comedy",
            null,
            null,
            "https://first.com"
        ),
        DBMedia(
            2L,
            "anime",
            "tv",
            MediaTitle("Second romaji", null, "Second"),
            "SUMMER",
            2020,
            "Second anime",
            null,
            null,
            12,
            "action, drama",
            null,
            null,
            "https://second.com"
        )
    )
    val studios: List<DBStudio> = listOf(
        DBStudio(
            1L,
            "First studio"
        ),
        DBStudio(
            2L,
            "Second studio"
        )
    )

    val firstScheduleItemEntity: DBScheduleItemEntity
    val secondScheduleItemEntity: DBScheduleItemEntity

    val firstNotificationItemEntity: DBNotificationItemEntity
    val secondNotificationItemEntity: DBNotificationItemEntity

    val firstFollowingItemEntity: DBFollowingItemEntity
    val secondFollowingItemEntity: DBFollowingItemEntity

    init {
        val firstMediaEntity = DBMediaEntity(
            media[0],
            listOf(
                DBMediaCharacterEntity(
                    DBMediaCharacter(
                        1L,
                        1L,
                        1L,
                        1L
                    ),
                    characters[0],
                    voiceActors[0]
                ),
                DBMediaCharacterEntity(
                    DBMediaCharacter(
                        2L,
                        1L,
                        2L,
                        2L
                    ),
                    characters[1],
                    voiceActors[1]
                )
            ),
            listOf(
                DBMediaStudioEntity(
                    DBMediaStudio(
                        1L,
                        1L,
                        1L
                    ),
                    studios[0]
                )
            ),
            listOf(
                DBMediaStaffEntity(
                    DBMediaStaff(
                        1L,
                        1L,
                        1L,
                        "director"
                    ),
                    staff[0]
                ),
                DBMediaStaffEntity(
                    DBMediaStaff(
                        2L,
                        1L,
                        2L,
                        "script"
                    ),
                    staff[1]
                )
            )
        )
        val firstScheduleItem = DBScheduleItem(
            1L,
            Calendar.getInstance().timeInMillis,
            2,
            1L
        )
        firstScheduleItemEntity = DBScheduleItemEntity(
            firstScheduleItem,
            firstMediaEntity
        )

        val secondMediaEntity = DBMediaEntity(
            media[1],
            listOf(
                DBMediaCharacterEntity(
                    DBMediaCharacter(
                        3L,
                        2L,
                        2L,
                        2L
                    ),
                    characters[1],
                    voiceActors[1]
                ),
                DBMediaCharacterEntity(
                    DBMediaCharacter(
                        4L,
                        2L,
                        3L,
                        3L
                    ),
                    characters[2],
                    voiceActors[2]
                )
            ),
            listOf(
                DBMediaStudioEntity(
                    DBMediaStudio(
                        2L,
                        2L,
                        2L
                    ),
                    studios[1]
                )
            ),
            listOf(
                DBMediaStaffEntity(
                    DBMediaStaff(
                        3L,
                        2L,
                        2L,
                        "director"
                    ),
                    staff[1]
                ),
                DBMediaStaffEntity(
                    DBMediaStaff(
                        4L,
                        2L,
                        3L,
                        "script"
                    ),
                    staff[2]
                )
            )
        )
        val secondScheduleItem = DBScheduleItem(
            2L,
            Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            6,
            2L
        )
        secondScheduleItemEntity = DBScheduleItemEntity(
            secondScheduleItem,
            secondMediaEntity
        )

        firstNotificationItemEntity = DBNotificationItemEntity(
            DBNotificationItem(
                1L,
                1L,
                System.currentTimeMillis(),
                1
            ),
            firstMediaEntity
        )

        secondNotificationItemEntity = DBNotificationItemEntity(
            DBNotificationItem(
                2L,
                2L,
                System.currentTimeMillis(),
                1
            ),
            secondMediaEntity
        )

        firstFollowingItemEntity = DBFollowingItemEntity(
            DBFollowingItem(
                1L,
                1L,
                "CURRENT"
            ),
            firstMediaEntity
        )

        secondFollowingItemEntity = DBFollowingItemEntity(
            DBFollowingItem(
                2L,
                2L,
                "CURRENT"
            ),
            secondMediaEntity
        )
    }

    fun getScheduleSampleData(): List<DBScheduleItemEntity> {
        return listOf(firstScheduleItemEntity, secondScheduleItemEntity)
    }

    fun getNotificationsSampleData(): List<DBNotificationItemEntity> {
        return listOf(firstNotificationItemEntity, secondNotificationItemEntity)
    }

    fun getFollowingSampleData(): List<DBFollowingItemEntity> {
        return listOf(firstFollowingItemEntity, secondFollowingItemEntity)
    }
}