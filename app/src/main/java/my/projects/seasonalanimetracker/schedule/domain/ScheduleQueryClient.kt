package my.projects.seasonalanimetracker.schedule.domain

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Single
import io.reactivex.SingleEmitter
import my.projects.seasonalanimetracker.SchedulesPageInfoQuery
import my.projects.seasonalanimetracker.SchedulesQuery
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.domain.mapper.query.ScheduleQueryToDataMapper
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

interface IScheduleQueryClient {
    fun getPagesCount(startDate: Calendar, endDate: Calendar): Single<Int>
    fun getPage(startDate: Calendar, endDate: Calendar, page: Int): Single<List<ScheduleMediaItem>>
}

class ScheduleQueryClient @Inject constructor(
    private val apolloClient: ApolloClient,
    private val mapper: ScheduleQueryToDataMapper
): IScheduleQueryClient {

    override fun getPagesCount(startDate: Calendar, endDate: Calendar): Single<Int> {
        return Single.defer<Int> {
            return@defer Single.create { emitter ->
                apolloClient.query(getPagesCountQuery(startDate, endDate)).enqueue(PageInfoCallback(emitter))
            }
        }
    }

    private fun getPagesCountQuery(startDate: Calendar, endDate: Calendar): SchedulesPageInfoQuery {
        return SchedulesPageInfoQuery((startDate.timeInMillis / 1000).toInt(), (endDate.timeInMillis / 1000).toInt())
    }

    override fun getPage(startDate: Calendar, endDate: Calendar, page: Int): Single<List<ScheduleMediaItem>> {
        return Single.defer<List<ScheduleMediaItem>> {
            return@defer Single.create { emitter ->
                apolloClient.query(getScheduleQuery(startDate, endDate, page)).enqueue(AccumulatorCallback(emitter))
            }
        }
    }

    private fun getScheduleQuery(startDate: Calendar, endDate: Calendar, page: Int): SchedulesQuery {
        Timber.d("loading schedule page ${startDate.timeInMillis / 1000} - ${endDate.timeInMillis / 1000}, $page")
        return SchedulesQuery((startDate.timeInMillis / 1000).toInt(), (endDate.timeInMillis / 1000).toInt(), page)
    }

    private inner class AccumulatorCallback(
        val emitter: SingleEmitter<List<ScheduleMediaItem>>
    ): ApolloCall.Callback<SchedulesQuery.Data>() {

        override fun onFailure(e: ApolloException) {
            Timber.e(e)
            emitter.onError(IOException(e.message))
        }

        override fun onResponse(response: Response<SchedulesQuery.Data>) {
            Timber.d("Loaded ${response.data()?.page?.airingSchedules?.size} schedule items")
            val responseItems: List<ScheduleMediaItem> = response.data()?.page?.airingSchedules?.mapNotNull { schedule ->
                if (schedule == null) {
                    null
                } else {
                    mapper.map(schedule)
                }
            } ?: emptyList()
            Timber.d("Mapped ${responseItems.size} schedule items")
            emitter.onSuccess(responseItems)
        }
    }

    private inner class PageInfoCallback(
        val emitter: SingleEmitter<Int>
    ): ApolloCall.Callback<SchedulesPageInfoQuery.Data>() {

        override fun onFailure(e: ApolloException) {
            Timber.e(e)
            emitter.onError(IOException(e.message))
        }

        override fun onResponse(response: Response<SchedulesPageInfoQuery.Data>) {
            emitter.onSuccess(response.data()?.page?.pageInfo?.fragments?.pageInfoFragment?.lastPage ?: 0)
        }

    }

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class QueryClientModule {
    @Binds
    abstract fun bindsQueryClient(queryClient: ScheduleQueryClient): IScheduleQueryClient
}