package my.projects.seasonalanimetracker.following.domain.db

import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Completable
import io.reactivex.Observable
import my.projects.seasonalanimetracker.app.testing.Mockable
import my.projects.seasonalanimetracker.db.MediaDatabase
import my.projects.seasonalanimetracker.db.dao.MediaDAO
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItem
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItemEntity
import javax.inject.Singleton

@Dao
@Mockable
abstract class FollowingDAO: MediaDAO() {

    @Query("select * from following")
    abstract fun getFollowing(): Observable<List<DBFollowingItemEntity>>

    @Insert
    protected abstract fun saveFollowingItem(itemEntity: DBFollowingItem)

    @Query("delete from following")
    abstract fun clearFollowingItems()

    @Transaction
    open fun saveFollowingItems(items: List<DBFollowingItemEntity>) {
        clearFollowingItems()
        for (item in items) {
            saveMediaEntity(item.mediaEntity)
            saveFollowingItem(item.followingItem)
        }
    }

    @Query("delete from following where following.id = :followingId")
    abstract fun deleteFromFollowing(followingId: Long): Completable

    @Query("update following set status = :status where id = :followingId")
    protected abstract fun updateFollowStatus(followingId: Long, status: String)

    @Transaction
    open fun updateFollowingStatus(followingId: Long, mediaId: Long, status: String) {
        updateMediaStatus(mediaId, status)
        updateFollowStatus(followingId, status)
    }
}

@Module
@InstallIn(ApplicationComponent::class)
class FollowingDaoModule {
    @Provides
    fun providesFollowingDAO(db: MediaDatabase): FollowingDAO {
        return db.followingDao()
    }
}