package my.projects.seasonalanimetracker.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.db.DBTestDataProvider
import my.projects.seasonalanimetracker.db.dao.ScheduleDAO
import my.projects.seasonalanimetracker.db.data.schedule.DBScheduleItemEntity
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.domain.IScheduleDateSource
import my.projects.seasonalanimetracker.schedule.domain.IScheduleLoader
import my.projects.seasonalanimetracker.schedule.domain.ScheduleDataSource
import my.projects.seasonalanimetracker.schedule.domain.mapper.db.ScheduleDataToEntityMapper
import my.projects.seasonalanimetracker.schedule.domain.mapper.db.ScheduleEntityToDataMapper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.time.DayOfWeek
import java.util.*

@RunWith(AndroidJUnit4::class)
class TestScheduleDataSource {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDataProvider = DBTestDataProvider()
    private val entityToDataMapper = ScheduleEntityToDataMapper()

    private val mockScheduleDAO = Mockito.mock(ScheduleDAO::class.java)
    private val mockScheduleLoader = Mockito.mock(IScheduleLoader::class.java)
    private val mockScheduleDateSource = Mockito.mock(IScheduleDateSource::class.java)

    private val scheduleDataSource = ScheduleDataSource(
        mockScheduleDAO,
        ScheduleEntityToDataMapper(),
        ScheduleDataToEntityMapper(),
        mockScheduleLoader,
        mockScheduleDateSource
    )

    @Test
    fun returnsStoredSchedule() {
        Mockito.`when`(mockScheduleDAO.getSchedule()).thenReturn(getScheduleDBEntityObservable())
        Mockito.`when`(mockScheduleDateSource.getStartDate()).thenReturn(Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, -1) })

        val schedule = scheduleDataSource.getSchedule().blockingFirst()

        assertEquals(2, schedule.values.flatten().size)
    }

    private fun getScheduleDBEntityObservable(): Observable<List<DBScheduleItemEntity>> {
        return Observable.just(testDataProvider.getScheduleSampleData())
    }

    @Test
    fun updatesSchedule() {
        Mockito.`when`(mockScheduleDateSource.getCurrentDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleDateSource.getLastUpdateDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleDateSource.getStartDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleDateSource.getEndDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleLoader.loadSchedule(any(), any()))
            .thenReturn(Single.just(testDataProvider.getScheduleSampleData().map { entityToDataMapper.map(it) }))

        scheduleDataSource.updateSchedule().blockingAwait()

        Mockito.verify(mockScheduleLoader).loadSchedule(any(), any())
        Mockito.verify(mockScheduleDAO).saveSchedule(eq(testDataProvider.getScheduleSampleData()))
    }

    @Test
    fun clearsAndUpdatesScheduleAfterLastUpdate() {
        Mockito.`when`(mockScheduleDateSource.getCurrentDate()).thenReturn(Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, 1) })
        Mockito.`when`(mockScheduleDateSource.getLastUpdateDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleDateSource.getStartDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleDateSource.getEndDate()).thenReturn(Calendar.getInstance())
        Mockito.`when`(mockScheduleLoader.loadSchedule(any(), any()))
            .thenReturn(Single.just(testDataProvider.getScheduleSampleData().map { entityToDataMapper.map(it) }))

        scheduleDataSource.updateSchedule().blockingAwait()

        Mockito.verify(mockScheduleDAO).clearSchedules()
        Mockito.verify(mockScheduleDateSource).saveUpdateDate(any())
        Mockito.verify(mockScheduleLoader).loadSchedule(any(), any())
        Mockito.verify(mockScheduleDAO).saveSchedule(eq(testDataProvider.getScheduleSampleData()))
    }

}