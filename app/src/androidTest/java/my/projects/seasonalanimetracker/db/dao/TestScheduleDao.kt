package my.projects.seasonalanimetracker.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.db.DBTestDataProvider
import my.projects.seasonalanimetracker.db.MediaDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestScheduleDao {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MediaDatabase
    private lateinit var scheduleDAO: ScheduleDAO
    private val dataProvider = DBTestDataProvider()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MediaDatabase::class.java).allowMainThreadQueries().build()
        scheduleDAO = db.scheduleDao()
    }

    @Test
    fun insertsAndReturns() {
        scheduleDAO.saveSchedule(dataProvider.getScheduleSampleData())

        val items = scheduleDAO.getSchedule().blockingFirst()

        assertEquals(dataProvider.getScheduleSampleData().size, items.size)
        assertEquals(dataProvider.getScheduleSampleData()[0], items[0])
        assertEquals(dataProvider.getScheduleSampleData()[1], items[1])
    }

    @Test
    fun overwritesScheduleData() {
        scheduleDAO.saveSchedule(dataProvider.getScheduleSampleData())

        scheduleDAO.saveSchedule(dataProvider.getScheduleSampleData().subList(0, 1))
        val items = scheduleDAO.getSchedule().blockingFirst()

        assertEquals(1, items.size)
        assertEquals(dataProvider.getScheduleSampleData()[0], items[0])
    }

    @Test
    fun deleteDoesNotCascade() {
        scheduleDAO.saveSchedule(dataProvider.getScheduleSampleData())

        scheduleDAO.clearSchedules()

        val items = scheduleDAO.getSchedule().blockingFirst()
        assertEquals(0, items.size)
        assertEquals(db.query("select * from shows", emptyArray()).count, 2)
    }
}