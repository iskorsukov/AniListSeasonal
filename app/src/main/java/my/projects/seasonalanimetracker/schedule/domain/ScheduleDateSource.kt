package my.projects.seasonalanimetracker.schedule.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import java.util.*
import javax.inject.Inject

interface IScheduleDateSource {
    fun getStartDate(): Calendar
    fun getEndDate(): Calendar
}

class ScheduleDateSource @Inject constructor(): IScheduleDateSource {

    private fun getCurrentDate(): Calendar {
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
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ScheduleDateSourceModule {

    @Binds
    abstract fun bindsScheduleDateSource(scheduleDateSource: ScheduleDateSource): IScheduleDateSource

}