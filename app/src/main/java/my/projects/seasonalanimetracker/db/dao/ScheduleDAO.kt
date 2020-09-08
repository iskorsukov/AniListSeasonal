package my.projects.seasonalanimetracker.db.dao

import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Observable
import my.projects.seasonalanimetracker.app.testing.Mockable
import my.projects.seasonalanimetracker.db.MediaDatabase
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItem
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity
import javax.inject.Singleton

@Dao
@Mockable
abstract class ScheduleDAO: MediaDAO() {

    @Query("select * from schedules")
    @Transaction
    abstract fun getSchedule(): Observable<List<DBScheduleItemEntity>>

    @Insert
    protected abstract fun saveScheduleItem(scheduleItem: DBScheduleItem)

    @Query("delete from schedules")
    abstract fun clearSchedules()

    @Transaction
    open fun saveSchedule(schedules: List<DBScheduleItemEntity>) {
        clearSchedules()
        for (schedule in schedules) {
            saveMediaEntity(schedule.mediaEntity)
            saveScheduleItem(schedule.scheduleItem)
        }
    }
}

@Module
@InstallIn(ApplicationComponent::class)
class ScheduleDAOModule {
    @Provides
    fun providesScheduleDao(db: MediaDatabase): ScheduleDAO {
        return db.scheduleDao()
    }
}