package my.projects.seasonalanimetracker.schedule.viewobject

import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.time.DayOfWeek

interface IScheduleVO {
    fun scheduleItems(): Map<DayOfWeek, List<ScheduleMediaItem>>
}

class ScheduleVO(private val scheduleItems: Map<DayOfWeek, List<ScheduleMediaItem>>): IScheduleVO {

    override fun scheduleItems(): Map<DayOfWeek, List<ScheduleMediaItem>> {
        return scheduleItems
    }

}