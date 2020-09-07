package my.projects.seasonalanimetracker.schedule.domain.mapper.db

import my.projects.seasonalanimetracker.app.common.domain.mapper.MediaEntityToDataMapper
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import javax.inject.Inject

class ScheduleEntityToDataMapper @Inject constructor(): MediaEntityToDataMapper() {

    fun map(entity: DBScheduleItemEntity): ScheduleMediaItem {
        return ScheduleMediaItem(
            id = entity.scheduleItem.id,
            airingAt = entity.scheduleItem.airingAt,
            episode = entity.scheduleItem.episode,
            media = mapMedia(entity.mediaEntity)
        )
    }
}