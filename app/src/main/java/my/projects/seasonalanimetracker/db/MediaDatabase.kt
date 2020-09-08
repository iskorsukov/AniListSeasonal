package my.projects.seasonalanimetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import my.projects.seasonalanimetracker.db.dao.MediaDAO
import my.projects.seasonalanimetracker.db.dao.NotificationsDAO
import my.projects.seasonalanimetracker.db.dao.ScheduleDAO
import my.projects.seasonalanimetracker.db.data.characters.DBCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBVoiceActor
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItem
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItem
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItem
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaff
import my.projects.seasonalanimetracker.db.data.staff.DBStaff
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudio
import my.projects.seasonalanimetracker.db.data.studios.DBStudio
import my.projects.seasonalanimetracker.following.domain.db.FollowingDAO
import javax.inject.Singleton

@Database(entities = [
    DBCharacter::class,
    DBVoiceActor::class,
    DBMediaCharacter::class,
    DBStaff::class,
    DBMediaStaff::class,
    DBStudio::class,
    DBMediaStudio::class,
    DBMedia::class,
    DBScheduleItem::class,
    DBNotificationItem::class,
    DBFollowingItem::class
], version = 1)
abstract class MediaDatabase: RoomDatabase() {
    abstract fun mediaDao(): MediaDAO
    abstract fun scheduleDao(): ScheduleDAO
    abstract fun notificationsDao(): NotificationsDAO
    abstract fun followingDao(): FollowingDAO
}

@Module
@InstallIn(ApplicationComponent::class)
class MediaDatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MediaDatabase {
        return Room.databaseBuilder(context, MediaDatabase::class.java, "media-db").build()
    }
}