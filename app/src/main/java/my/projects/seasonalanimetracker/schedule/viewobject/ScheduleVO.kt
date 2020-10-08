package my.projects.seasonalanimetracker.schedule.viewobject

import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.time.DayOfWeek

interface IScheduleVO {
    fun scheduleItems(): Map<DayOfWeek, List<ScheduleMediaItem>>
    fun isAuth(): Boolean
}

class ScheduleVO(private val scheduleItems: Map<DayOfWeek, List<ScheduleMediaItem>>, private val isAuth: Boolean): IScheduleVO {

    override fun scheduleItems(): Map<DayOfWeek, List<ScheduleMediaItem>> {
        return scheduleItems
    }

    override fun isAuth(): Boolean {
        return isAuth
    }

}