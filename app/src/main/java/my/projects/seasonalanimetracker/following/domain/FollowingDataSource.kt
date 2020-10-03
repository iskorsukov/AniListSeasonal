package my.projects.seasonalanimetracker.following.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItemEntity
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.domain.db.FollowingDAO
import my.projects.seasonalanimetracker.following.domain.mapper.db.FollowingDataToEntityMapper
import my.projects.seasonalanimetracker.following.domain.mapper.db.FollowingEntityToDataMapper
import my.projects.seasonalanimetracker.following.domain.mapper.query.FollowingQueryToDataMapper
import my.projects.seasonalanimetracker.schedule.domain.ScheduleDateSource
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

interface IFollowingDataSource {
    fun getFollowing(): Observable<List<FollowingMediaItem>>
    fun updateFollowing(): Completable
    fun getCurrentSeason(): Pair<String, Int>
    fun updateFollowStatus(item: FollowingMediaItem, status: String): Completable
    fun removeFromFollowing(item: FollowingMediaItem): Completable
}

class FollowingDataSource @Inject constructor(
    private val followingDAO: FollowingDAO,
    private val loader: IFollowingLoader,
    private val dataToEntityMapper: FollowingDataToEntityMapper,
    private val entityToDataMapper: FollowingEntityToDataMapper,
    private val followingSeasonSource: FollowingSeasonSource
): IFollowingDataSource {

    override fun getFollowing(): Observable<List<FollowingMediaItem>> {
        return followingDAO.getFollowing().map {
            Timber.i("Got ${it.size} entities")
            it.map { entityToDataMapper.map(it) }
        }
    }

    override fun updateFollowing(): Completable {
        return loadFollowing().flatMapCompletable {
            storeFollowing(it)
        }
    }

    private fun loadFollowing(): Single<List<FollowingMediaItem>> {
        return loader.loadFollowing()
    }

    private fun storeFollowing(items: List<FollowingMediaItem>): Completable {
        return Completable.fromAction {
            Timber.i("Storing ${items.size} entities")
            followingDAO.saveFollowingItems(items.map { dataToEntityMapper.map(it) })
        }.subscribeOn(Schedulers.io())
    }

    override fun getCurrentSeason(): Pair<String, Int> {
        return followingSeasonSource.getCurrentSeason() to followingSeasonSource.getCurrentYear()
    }

    override fun updateFollowStatus(item: FollowingMediaItem, status: String): Completable {
        return loader.updateFollowStatus(item.id.toInt(), status).flatMapCompletable { updated ->
            if (updated) {
                Timber.d("Status updated")
                Completable.fromAction {
                    followingDAO.updateFollowingStatus(item.id, item.media.id, status)
                }
            } else {
                Completable.error(IOException("Failed to update media status"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun removeFromFollowing(item: FollowingMediaItem): Completable {
        return loader.removeFromFollowing(item.id.toInt()).flatMapCompletable { deleted ->
            if (deleted) {
                Timber.d("Request deleted")
                followingDAO.deleteFromFollowing(item.id).doOnComplete { Timber.d("Deleted from db") }
            } else {
                Completable.error(IOException("Failed to delete media entry"))
            }
        }.subscribeOn(Schedulers.io())
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingDataSourceModule {
    @Binds
    abstract fun bindsFollowingDataSource(followingDataSource: FollowingDataSource): IFollowingDataSource
}