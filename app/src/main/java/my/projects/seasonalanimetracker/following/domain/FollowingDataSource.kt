package my.projects.seasonalanimetracker.following.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import my.projects.seasonalanimetracker.db.data.following.DBFollowingItemEntity
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.domain.db.FollowingDAO
import my.projects.seasonalanimetracker.following.domain.mapper.db.FollowingDataToEntityMapper
import my.projects.seasonalanimetracker.following.domain.mapper.db.FollowingEntityToDataMapper
import my.projects.seasonalanimetracker.following.domain.mapper.query.FollowingQueryToDataMapper
import my.projects.seasonalanimetracker.schedule.domain.ScheduleDateSource
import javax.inject.Inject

interface IFollowingDataSource {
    fun getFollowing(): Observable<List<FollowingMediaItem>>
    fun updateFollowing(): Completable
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
            it.map { entityToDataMapper.map(it) }
        }
    }

    override fun updateFollowing(): Completable {
        return loadFollowing().flatMapCompletable {
            storeFollowing(it)
        }
    }

    private fun loadFollowing(): Single<List<FollowingMediaItem>> {
        return loader.loadFollowing(followingSeasonSource.getCurrentSeason(), followingSeasonSource.getCurrentYear())
    }

    private fun storeFollowing(items: List<FollowingMediaItem>): Completable {
        return Completable.fromAction {
            followingDAO.saveFollowingItems(items.map { dataToEntityMapper.map(it) })
        }
    }

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingDataSourceModule {
    @Binds
    abstract fun bindsFollowingDataSource(followingDataSource: FollowingDataSource): IFollowingDataSource
}