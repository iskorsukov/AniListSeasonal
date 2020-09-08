package my.projects.seasonalanimetracker.db.dao

import androidx.paging.DataSource
import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Single
import my.projects.seasonalanimetracker.db.MediaDatabase
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItem
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItemEntity
import javax.inject.Singleton

@Dao
abstract class NotificationsDAO: MediaDAO() {

    @Query("select * from notifications order by createdAt desc limit 50")
    @Transaction
    abstract fun getPagedNotifications(): DataSource.Factory<Int, DBNotificationItemEntity>

    @Query("select * from notifications order by createdAt limit 50")
    @Transaction
    abstract fun getNotifications(): Single<List<DBNotificationItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun saveNotificationItem(notificationItem: DBNotificationItem)

    @Query("delete from notifications")
    abstract fun clearNotifications()

    @Transaction
    open fun saveNotifications(notifications: List<DBNotificationItemEntity>) {
        for (notification in notifications) {
            saveMediaEntity(notification.mediaEntity)
            saveNotificationItem(notification.notificationItem)
        }
    }
}

@Module
@InstallIn(ApplicationComponent::class)
class NotificationsDAOModule {
    @Provides
    fun providesNotificationDao(db: MediaDatabase): NotificationsDAO {
        return db.notificationsDao()
    }
}