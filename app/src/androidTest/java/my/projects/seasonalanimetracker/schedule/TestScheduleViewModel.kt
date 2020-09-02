package my.projects.seasonalanimetracker.schedule

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Completable
import io.reactivex.Observable
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.domain.IScheduleDataSource
import my.projects.seasonalanimetracker.schedule.viewmodel.ScheduleViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.time.DayOfWeek

@RunWith(AndroidJUnit4::class)
class TestScheduleViewModel {

    private val mockScheduleDataSource = Mockito.mock(IScheduleDataSource::class.java)

    private val viewModel = ScheduleViewModel(mockScheduleDataSource)

    private fun getEmptySampleData(): Map<DayOfWeek, List<ScheduleMediaItem>> {
        val map = LinkedHashMap<DayOfWeek, List<ScheduleMediaItem>>()
        map[DayOfWeek.MONDAY] = emptyList()
        map[DayOfWeek.TUESDAY] = emptyList()
        map[DayOfWeek.WEDNESDAY] = emptyList()
        map[DayOfWeek.THURSDAY] = emptyList()
        map[DayOfWeek.FRIDAY] = emptyList()
        map[DayOfWeek.SATURDAY] = emptyList()
        map[DayOfWeek.SUNDAY] = emptyList()
        return map
    }

    @Before
    fun setUp() {
        Mockito.`when`(mockScheduleDataSource.getSchedule()).thenReturn(Observable.just(getEmptySampleData()))
        Mockito.`when`(mockScheduleDataSource.updateSchedule()).thenReturn(Completable.complete())
    }

    @Test
    fun liveDataCallsDataSource() {
        val liveData = viewModel.scheduleLD()

        Mockito.verify(mockScheduleDataSource).getSchedule()
    }

    @Test
    fun updatesSchedule() {
        viewModel.updateSchedule()

        Mockito.verify(mockScheduleDataSource).updateSchedule()
    }
}