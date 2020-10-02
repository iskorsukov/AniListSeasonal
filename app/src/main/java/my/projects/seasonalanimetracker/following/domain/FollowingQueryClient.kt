package my.projects.seasonalanimetracker.following.domain

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Single
import my.projects.seasonalanimetracker.FollowingPageQuery
import my.projects.seasonalanimetracker.FollowingPagesCountQuery
import my.projects.seasonalanimetracker.UnfollowMutation
import my.projects.seasonalanimetracker.UserIdQuery
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.domain.mapper.query.FollowingQueryToDataMapper
import java.io.IOException
import javax.inject.Inject

interface IFollowingQueryClient {
    fun getPagesCount(): Single<Int>
    fun getPage(page: Int): Single<List<FollowingMediaItem>>
    fun removeFromFollow(followingId: Int): Single<Boolean>
}

class FollowingQueryClient @Inject constructor(
    private val apolloClient: ApolloClient,
    private val queryToDataMapper: FollowingQueryToDataMapper
): IFollowingQueryClient {

    override fun getPagesCount(): Single<Int> {
        return getUserId().map { id -> getPagesCountQuery(id) }.flatMap { query ->
            Single.defer {
                return@defer Single.create { emitter ->
                    apolloClient.query(query).enqueue(object: ApolloCall.Callback<FollowingPagesCountQuery.Data>() {
                        override fun onResponse(response: Response<FollowingPagesCountQuery.Data>) {
                            emitter.onSuccess(response.data()?.page?.pageInfo?.fragments?.pageInfoFragment?.lastPage ?: 1)
                        }

                        override fun onFailure(e: ApolloException) {
                            emitter.onError(e)
                        }
                    })
                }
            }
        }
    }

    private fun getUserId(): Single<Int> {
        return Single.create { emitter ->
            apolloClient.query(UserIdQuery()).enqueue(object: ApolloCall.Callback<UserIdQuery.Data>() {
                override fun onResponse(response: Response<UserIdQuery.Data>) {
                    val id = response.data()?.viewer?.id
                    if (id == null) {
                        emitter.onError(IOException("User id not received"))
                    } else {
                        emitter.onSuccess(id)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    emitter.onError(e)
                }
            })
        }
    }

    private fun getPagesCountQuery(userId: Int): FollowingPagesCountQuery {
        return FollowingPagesCountQuery(Input.fromNullable(userId))
    }

    override fun getPage(page: Int): Single<List<FollowingMediaItem>> {
        return getUserId().map { id -> getPageQuery(id, page) }.flatMap { query ->
            Single.defer {
                return@defer Single.create { emitter ->
                    apolloClient.query(query).enqueue(object: ApolloCall.Callback<FollowingPageQuery.Data>() {
                        override fun onResponse(response: Response<FollowingPageQuery.Data>) {
                            emitter.onSuccess(response.data()!!.page!!.mediaList!!.map { queryToDataMapper.map(it!!) })
                        }

                        override fun onFailure(e: ApolloException) {
                            emitter.onError(e)
                        }
                    })
                }
            }
        }
    }

    private fun getPageQuery(userId: Int, page: Int): FollowingPageQuery {
        return FollowingPageQuery(Input.fromNullable(userId), Input.fromNullable(page))
    }

    override fun removeFromFollow(followingId: Int): Single<Boolean> {
        return Single.defer {
            return@defer Single.create { emitter ->
                apolloClient
                    .mutate(UnfollowMutation(Input.fromNullable(followingId)))
                    .enqueue(object : ApolloCall.Callback<UnfollowMutation.Data>() {
                        override fun onResponse(response: Response<UnfollowMutation.Data>) {
                            emitter.onSuccess(response.data()!!.deleteMediaListEntry!!.deleted!!)
                        }

                        override fun onFailure(e: ApolloException) {
                            emitter.onError(e)
                        }
                    })
            }
        }
    }

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingQueryClientModule {
    @Binds
    abstract fun bindsFollowingQueryClient(followingQueryClient: FollowingQueryClient): IFollowingQueryClient
}