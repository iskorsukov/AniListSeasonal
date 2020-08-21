package my.projects.seasonalanimetracker.schedule.domain

import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import java.util.*
import javax.inject.Inject

interface IScheduleDateSource {
    fun getCurrentDate(): Calendar
    fun getStartDate(): Calendar
    fun getEndDate(): Calendar
    fun getLastUpdateDate(): Calendar
    fun saveUpdateDate(date: Calendar)
}

class ScheduleDateSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): IScheduleDateSource {

    companion object {
        const val LAST_UPDATE_DATE_KEY = "last_update_date"
    }

    override fun getCurrentDate(): Calendar {
        return Calendar.getInstance().also { date ->
            date.set(Calendar.HOUR, 0)
            date.set(Calendar.MINUTE, 0)
            date.set(Calendar.SECOND, 0)
            date.set(Calendar.MILLISECOND, 0)
        }
    }

    override fun getStartDate(): Calendar {
        return Calendar.getInstance().also { date ->
            date.time = getCurrentDate().time
            date.add(Calendar.DATE, -1)
        }
    }

    override fun getEndDate(): Calendar {
        return Calendar.getInstance().also { date ->
            date.time = getCurrentDate().time
            date.add(Calendar.DATE, 5)
        }
    }

    override fun getLastUpdateDate(): Calendar {
        return Calendar.getInstance().also {
            it.timeInMillis = sharedPreferences.getLong(LAST_UPDATE_DATE_KEY, 0L)
        }
    }

    override fun saveUpdateDate(date: Calendar) {
        val editor = sharedPreferences.edit()
        editor.putLong(LAST_UPDATE_DATE_KEY, date.timeInMillis)
        editor.apply()
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ScheduleDateSourceModule {

    @Binds
    abstract fun bindsScheduleDateSource(scheduleDateSource: ScheduleDateSource): IScheduleDateSource

}