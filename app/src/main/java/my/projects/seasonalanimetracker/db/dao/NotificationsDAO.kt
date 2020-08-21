package my.projects.seasonalanimetracker.db.dao

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Observable
import my.projects.seasonalanimetracker.db.MediaDatabase
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItem
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItemEntity
import timber.log.Timber

@Dao
abstract class NotificationsDAO: MediaDAO() {

    @Query("select * from notifications order by createdAt desc")
    @Transaction
    abstract fun getNotifications(): DataSource.Factory<Int, DBNotificationItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveNotificationItem(notificationItem: DBNotificationItem)

    @Query("delete from notifications")
    protected abstract fun clearNotifications()

    @Transaction
    open fun saveNotifications(notifications: List<DBNotificationItemEntity>) {
        clearNotifications()
        for (notification in notifications) {
            for (characterEntity in notification.mediaEntity.characterEntities) {
                saveCharacter(characterEntity.character)
                characterEntity.voiceActor?.let {
                    saveVoiceActor(it)
                }
                saveMediaCharacter(characterEntity.mediaCharacter)
            }
            for (staffEntity in notification.mediaEntity.staffEntities) {
                saveStaff(staffEntity.staff)
                saveMediaStaff(staffEntity.mediaStaff)
            }
            for (studioEntity in notification.mediaEntity.studioEntities) {
                saveStudio(studioEntity.studio)
                saveMediaStudio(studioEntity.mediaStudio)
            }
            saveMedia(notification.mediaEntity.media)
            saveNotificationItem(notification.notificationItem)
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
class NotificationsDAOModule {
    @Provides
    fun providesNotificationDao(db: MediaDatabase): NotificationsDAO {
        return db.notificationsDao()
    }
}