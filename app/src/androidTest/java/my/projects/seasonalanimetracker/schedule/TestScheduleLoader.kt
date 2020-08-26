package my.projects.seasonalanimetracker.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.anyOrNull
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.schedule.domain.IScheduleQueryClient
import my.projects.seasonalanimetracker.schedule.domain.IScheduleLoader
import my.projects.seasonalanimetracker.schedule.domain.ScheduleLoader
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

@RunWith(AndroidJUnit4::class)
class TestScheduleLoader {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockQueryClient = mock(IScheduleQueryClient::class.java)
    private val scheduleLoader: IScheduleLoader = ScheduleLoader(mockQueryClient)

    private val sampleData = getSampleData()

    @Test
    fun loadsSchedule() {
        mockQueryResponses(1, sampleData)

        val items = scheduleLoader.loadSchedule(Calendar.getInstance(), Calendar.getInstance()).blockingGet()

        assertEquals(sampleData.size, items.size)
        assertEquals(sampleData[0], items[0])
        assertEquals(sampleData[1], items[1])
    }

    @Test
    fun loadsMultiPageSchedule() {
        mockQueryResponses(2, sampleData)

        val items = scheduleLoader.loadSchedule(Calendar.getInstance(), Calendar.getInstance()).blockingGet()

        assertEquals(sampleData.size * 2, items.size)
    }

    private fun mockQueryResponses(pages: Int, data: List<ScheduleMediaItem>) {
        `when`(mockQueryClient.getPagesCount(anyOrNull(), anyOrNull())).thenReturn(Single.just(pages))
        `when`(mockQueryClient.getPage(anyOrNull(), anyOrNull(), ArgumentMatchers.anyInt()))
            .thenReturn(Single.just(data))
    }

    private fun getSampleData(): List<ScheduleMediaItem> {
        return listOf(
            ScheduleMediaItem(
                1L,
                1L,
                1,
                mock(Media::class.java)
            ),
            ScheduleMediaItem(
                2L,
                2L,
                1,
                mock(Media::class.java)
            )
        )
    }
}