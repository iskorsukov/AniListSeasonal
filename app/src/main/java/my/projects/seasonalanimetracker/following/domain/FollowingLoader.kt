package my.projects.seasonalanimetracker.following.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Single
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import timber.log.Timber
import javax.inject.Inject

interface IFollowingLoader {
    fun loadFollowing(season: String, seasonYear: Int): Single<List<FollowingMediaItem>>
    fun removeFromFollowing(followingId: Int): Single<Boolean>
}

class FollowingLoader @Inject constructor(
    private val queryClient: IFollowingQueryClient
): IFollowingLoader {

    override fun loadFollowing(season: String, seasonYear: Int): Single<List<FollowingMediaItem>> {
        return queryClient.getPagesCount().flatMap {
            val requests = mutableListOf<Single<List<FollowingMediaItem>>>()
            for (i in 1..it) {
                requests.add(queryClient.getPage(i))
            }
            Single.concat(requests).reduce(mutableListOf<FollowingMediaItem>(), { seed, input ->
                seed.addAll(input)
                seed
            }).map {
                Timber.i("Filtering ${it.size} items")
                Timber.i(it.map { "${it.media.titleNative} - ${it.media.season} - ${it.media.seasonYear}" }.joinToString(separator = "\n"))
                it.filter { item -> item.media.season == season && item.media.seasonYear == seasonYear }
            }
        }
    }

    override fun removeFromFollowing(followingId: Int): Single<Boolean> {
        return queryClient.removeFromFollow(followingId)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingLoaderModule {
    @Binds
    abstract fun bindsFollowingLoader(followingLoader: FollowingLoader): IFollowingLoader
}