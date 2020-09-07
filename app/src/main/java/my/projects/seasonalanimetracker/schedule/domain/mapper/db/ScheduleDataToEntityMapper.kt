package my.projects.seasonalanimetracker.schedule.domain.mapper.db

import my.projects.seasonalanimetracker.app.common.domain.mapper.MediaDataToEntityMapper
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItem
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import javax.inject.Inject

class ScheduleDataToEntityMapper @Inject constructor(): MediaDataToEntityMapper() {

    fun map(schedule: ScheduleMediaItem): DBScheduleItemEntity {
        return DBScheduleItemEntity(
            DBScheduleItem(
                schedule.id,
                schedule.airingAt,
                schedule.episode,
                schedule.media.id
            ),
            mapMediaEntity(schedule.media)
        )
    }
}