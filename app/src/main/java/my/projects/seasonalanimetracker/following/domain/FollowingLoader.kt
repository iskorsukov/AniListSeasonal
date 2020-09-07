package my.projects.seasonalanimetracker.following.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Single
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import javax.inject.Inject

interface IFollowingLoader {
    fun loadFollowing(season: String, seasonYear: Int): Single<List<FollowingMediaItem>>
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
                it.filter { item -> item.media.season == season && item.media.seasonYear == seasonYear }
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingLoaderModule {
    @Binds
    abstract fun bindsFollowingLoader(followingLoader: FollowingLoader): IFollowingLoader
}