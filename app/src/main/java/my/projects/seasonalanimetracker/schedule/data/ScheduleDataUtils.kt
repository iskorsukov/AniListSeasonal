package my.projects.seasonalanimetracker.schedule.data

import java.time.DayOfWeek
import java.util.*

object ScheduleDataUtils {

    fun dayOfWeekFromMillis(timeInMillis: Long): DayOfWeek {
        val calendar = GregorianCalendar.getInstance().also {
            it.timeInMillis = timeInMillis
        }
        val dayOfWeek: DayOfWeek
        var intDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        // necessary translation to be consistent with DayOfWeek values
        if (intDayOfWeek >= 2) {
            intDayOfWeek -= 2
        } else {
            intDayOfWeek = 6 // SUNDAY
        }
        dayOfWeek = DayOfWeek.values()[intDayOfWeek]
        return dayOfWeek
    }
}