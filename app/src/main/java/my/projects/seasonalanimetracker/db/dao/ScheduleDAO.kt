package my.projects.seasonalanimetracker.db.dao

import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Observable
import my.projects.seasonalanimetracker.db.MediaDatabase
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItem
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity

@Dao
abstract class ScheduleDAO: MediaDAO() {

    @Query("select * from schedules")
    @Transaction
    abstract fun getSchedule(): Observable<List<DBScheduleItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveScheduleItem(scheduleItem: DBScheduleItem)

    @Query("delete from schedules")
    protected abstract fun clearSchedules()

    @Transaction
    open fun saveSchedule(schedules: List<DBScheduleItemEntity>) {
        clearSchedules()
        for (schedule in schedules) {
            for (characterEntity in schedule.mediaEntity.characterEntities) {
                saveCharacter(characterEntity.character)
                characterEntity.voiceActor?.let {
                    saveVoiceActor(it)
                }
                saveMediaCharacter(characterEntity.mediaCharacter)
            }
            for (staffEntity in schedule.mediaEntity.staffEntities) {
                saveStaff(staffEntity.staff)
                saveMediaStaff(staffEntity.mediaStaff)
            }
            for (studioEntity in schedule.mediaEntity.studioEntities) {
                saveStudio(studioEntity.studio)
                saveMediaStudio(studioEntity.mediaStudio)
            }
            saveMedia(schedule.mediaEntity.media)
            saveScheduleItem(schedule.scheduleItem)
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
class ScheduleDAOModule {
    @Provides
    fun providesScheduleDao(db: MediaDatabase): ScheduleDAO {
        return db.scheduleDao()
    }
}