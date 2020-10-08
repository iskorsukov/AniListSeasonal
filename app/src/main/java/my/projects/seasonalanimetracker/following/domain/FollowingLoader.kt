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
    fun loadFollowing(): Single<List<FollowingMediaItem>>
    fun addToFollowing(mediaId: Int, status: String): Single<Pair<Int, String>>
    fun updateFollowStatus(mediaId: Int, status: String): Single<Boolean>
    fun removeFromFollowing(mediaId: Int): Single<Boolean>
}

class FollowingLoader @Inject constructor(
    private val queryClient: IFollowingQueryClient
): IFollowingLoader {

    override fun loadFollowing(): Single<List<FollowingMediaItem>> {
        return queryClient.getPagesCount().flatMap {
            val requests = mutableListOf<Single<List<FollowingMediaItem>>>()
            for (i in 1..it) {
                requests.add(queryClient.getPage(i))
            }
            Single.concat(requests).reduce(mutableListOf<FollowingMediaItem>(), { seed, input ->
                seed.addAll(input)
                seed
            })
        }
    }

    override fun addToFollowing(mediaId: Int, status: String): Single<Pair<Int, String>> {
        return queryClient.addToFollow(mediaId, status)
    }

    override fun updateFollowStatus(mediaId: Int, status: String): Single<Boolean> {
        return queryClient.updateFollowStatus(mediaId, status)
    }

    override fun removeFromFollowing(mediaId: Int): Single<Boolean> {
        return queryClient.getFollowIdForMediaId(mediaId).flatMap { id -> queryClient.removeFromFollow(id) }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingLoaderModule {
    @Binds
    abstract fun bindsFollowingLoader(followingLoader: FollowingLoader): IFollowingLoader
}