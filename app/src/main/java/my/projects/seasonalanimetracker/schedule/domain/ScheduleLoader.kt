package my.projects.seasonalanimetracker.schedule.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.util.*
import javax.inject.Inject

interface IScheduleLoader {
    fun loadSchedule(startDate: Calendar, endDate: Calendar): Single<List<ScheduleMediaItem>>
}

class ScheduleLoader @Inject constructor(
    private val scheduleQueryClient: IScheduleQueryClient
): IScheduleLoader {

    override fun loadSchedule(startDate: Calendar, endDate: Calendar): Single<List<ScheduleMediaItem>> {
        return scheduleQueryClient.getPagesCount(startDate, endDate).flatMap { pagesCount ->
            val requests = mutableListOf<Single<List<ScheduleMediaItem>>>()
            for (page in 1..pagesCount) {
                requests.add(scheduleQueryClient.getPage(startDate, endDate, page))
            }
            Single.concat(requests).reduce(mutableListOf<ScheduleMediaItem>(), { seed, input ->
                seed.addAll(input)
                seed
            }).map {
                it.toList()
            }
        }.subscribeOn(Schedulers.io())
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ScheduleLoaderModule {
    @Binds
    abstract fun bindScheduleLoader(scheduleLoader: ScheduleLoader): IScheduleLoader
}