package my.projects.seasonalanimetracker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import my.projects.seasonalanimetracker.db.MediaDatabase
import my.projects.seasonalanimetracker.db.data.characters.DBCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBMediaCharacter
import my.projects.seasonalanimetracker.db.data.characters.DBVoiceActor
import my.projects.seasonalanimetracker.db.data.media.DBMedia
import my.projects.seasonalanimetracker.db.data.staff.DBMediaStaff
import my.projects.seasonalanimetracker.db.data.staff.DBStaff
import my.projects.seasonalanimetracker.db.data.studios.DBMediaStudio
import my.projects.seasonalanimetracker.db.data.studios.DBStudio

@Dao
abstract class MediaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveCharacter(character: DBCharacter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveVoiceActor(voiceActor: DBVoiceActor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveMediaCharacter(mediaCharacter: DBMediaCharacter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveStaff(staff: DBStaff)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveMediaStaff(mediaStaff: DBMediaStaff)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveStudio(studio: DBStudio)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveMediaStudio(mediaStudio: DBMediaStudio)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveMedia(media: DBMedia)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
class MediaDAOModule {
    @Provides
    fun providesMediaDao(db: MediaDatabase): MediaDAO {
        return db.mediaDao()
    }
}