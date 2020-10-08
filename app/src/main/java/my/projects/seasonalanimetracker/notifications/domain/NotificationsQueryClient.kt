package my.projects.seasonalanimetracker.notifications.domain

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Single
import io.reactivex.SingleEmitter
import my.projects.seasonalanimetracker.NotificationsQuery
import my.projects.seasonalanimetracker.UnreadNotificationsQuery
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import my.projects.seasonalanimetracker.notifications.domain.mapper.query.NotificationQueryToDataMapper
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

interface INotificationsQueryClient {
    fun getPage(): Single<List<NotificationMediaItem>>
    fun getPage(size: Int): Single<List<NotificationMediaItem>>
    fun getUnreadNotificationsCount(): Single<Int>
}

class NotificationsQueryClient @Inject constructor(
    private val apolloClient: ApolloClient,
    private val mapper: NotificationQueryToDataMapper
): INotificationsQueryClient {

    override fun getPage(): Single<List<NotificationMediaItem>> {
        return Single.defer<List<NotificationMediaItem>> {
            return@defer Single.create { emitter ->
                apolloClient.query(getNotificationsQuery()).enqueue(AccumulatorCallback(emitter))
            }
        }
    }

    override fun getPage(size: Int): Single<List<NotificationMediaItem>> {
        return Single.defer<List<NotificationMediaItem>> {
            return@defer Single.create { emitter ->
                apolloClient.query(getNotificationsQuery(size)).enqueue(AccumulatorCallback(emitter))
            }
        }
    }

    private fun getNotificationsQuery(size: Int = 50): NotificationsQuery {
        return NotificationsQuery(Input.fromNullable(size))
    }

    override fun getUnreadNotificationsCount(): Single<Int> {
        return Single.defer<Int> {
            return@defer Single.create { emitter ->
                apolloClient.query(UnreadNotificationsQuery()).enqueue(UnreadNotificationsCallback(emitter))
            }
        }
    }

    private inner class AccumulatorCallback(
        val emitter: SingleEmitter<List<NotificationMediaItem>>
    ): ApolloCall.Callback<NotificationsQuery.Data>() {

        override fun onFailure(e: ApolloException) {
            emitter.onError(IOException(e.message))
        }

        override fun onResponse(response: Response<NotificationsQuery.Data>) {
            Timber.d("Loaded ${response.data()?.page?.notifications?.size} notification items")
            val responseItems: List<NotificationMediaItem> = response.data()?.page?.notifications?.mapNotNull { notification ->
                if (notification?.asAiringNotification == null) {
                    null
                } else {
                    mapper.map(notification.asAiringNotification)
                }
            } ?: emptyList()
            Timber.d("Mapped ${responseItems.size} notification items")
            emitter.onSuccess(responseItems)
        }
    }

    private class UnreadNotificationsCallback(
        val emitter: SingleEmitter<Int>
    ): ApolloCall.Callback<UnreadNotificationsQuery.Data>() {

        override fun onResponse(response: Response<UnreadNotificationsQuery.Data>) {
            val count = response.data()?.viewer?.unreadNotificationCount ?: 0
            emitter.onSuccess(count)
        }

        override fun onFailure(e: ApolloException) {
            emitter.onError(IOException(e.message))
        }

    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class NotificationsQueryClientModule {
    @Binds
    abstract fun bindsNotificationsQueryClient(notificationsQueryClient: NotificationsQueryClient): INotificationsQueryClient
}