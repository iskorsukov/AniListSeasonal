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
import my.projects.seasonalanimetracker.*
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.domain.mapper.query.FollowingQueryToDataMapper
import my.projects.seasonalanimetracker.type.MediaListStatus
import java.io.IOException
import javax.inject.Inject

interface IFollowingQueryClient {
    fun getPagesCount(): Single<Int>
    fun getPage(page: Int): Single<List<FollowingMediaItem>>
    fun getFollowIdForMediaId(mediaId: Int): Single<Int>
    fun addToFollow(mediaId: Int, status: String): Single<Pair<Int, String>>
    fun updateFollowStatus(followingId: Int, status: String): Single<Boolean>
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
                    apolloClient
                        .query(query)
                        .enqueue(object: ApolloCall.Callback<FollowingPageQuery.Data>() {
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

    override fun getFollowIdForMediaId(mediaId: Int): Single<Int> {
        return Single.defer {
            return@defer Single.create { emitter ->
                apolloClient
                    .query(GetMediaListEntryIdByMediaIdQuery(Input.fromNullable(mediaId)))
                    .enqueue(object : ApolloCall.Callback<GetMediaListEntryIdByMediaIdQuery.Data>() {
                        override fun onResponse(response: Response<GetMediaListEntryIdByMediaIdQuery.Data>) {
                            emitter.onSuccess(response.data()!!.mediaList!!.id)
                        }

                        override fun onFailure(e: ApolloException) {
                            emitter.onError(e)
                        }
                    })
            }
        }
    }

    override fun addToFollow(mediaId: Int, status: String): Single<Pair<Int, String>> {
        return Single.defer {
            return@defer Single.create { emitter ->
                apolloClient
                    .mutate(
                        AddMediaListEntryMutation(
                            Input.fromNullable(mediaId),
                            Input.fromNullable(MediaListStatus.safeValueOf(status))
                        )
                    )
                    .enqueue(object  : ApolloCall.Callback<AddMediaListEntryMutation.Data>() {
                        override fun onResponse(response: Response<AddMediaListEntryMutation.Data>) {
                            val id = response.data()!!.saveMediaListEntry!!.id
                            val st = response.data()!!.saveMediaListEntry!!.status!!
                            emitter.onSuccess(id to st.name)
                        }

                        override fun onFailure(e: ApolloException) {
                            emitter.onError(e)
                        }
                    })
            }
        }
    }

    override fun updateFollowStatus(followingId: Int, status: String): Single<Boolean> {
        return Single.defer {
            return@defer Single.create { emitter ->
                apolloClient
                    .mutate(
                        UpdateMediaListEntryMutation(
                            Input.fromNullable(followingId),
                            Input.fromNullable(MediaListStatus.safeValueOf(status))
                        )
                    )
                    .enqueue(object : ApolloCall.Callback<UpdateMediaListEntryMutation.Data>() {
                        override fun onResponse(response: Response<UpdateMediaListEntryMutation.Data>) {
                            emitter.onSuccess(response.data()!!.saveMediaListEntry!!.status!!.name == status)
                        }

                        override fun onFailure(e: ApolloException) {
                            emitter.onError(e)
                        }
                    })
            }
        }
    }

    override fun removeFromFollow(followingId: Int): Single<Boolean> {
        return Single.defer {
            return@defer Single.create { emitter ->
                apolloClient
                    .mutate(
                        RemoveMediaListEntryMutation(
                            Input.fromNullable(followingId)
                        )
                    )
                    .enqueue(object : ApolloCall.Callback<RemoveMediaListEntryMutation.Data>() {
                        override fun onResponse(response: Response<RemoveMediaListEntryMutation.Data>) {
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