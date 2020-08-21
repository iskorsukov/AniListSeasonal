package my.projects.seasonalanimetracker.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.db.dao.MediaDAO
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMediaDao {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MediaDatabase
    private lateinit var mediaDao: MediaDAO
    private val dataProvider = DBTestDataProvider()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MediaDatabase::class.java).allowMainThreadQueries().build()
        mediaDao = db.mediaDao()
    }

    @Test
    fun insertsAndReturns() {
        mediaDao.saveSchedule(dataProvider.getSampleData())

        val items = mediaDao.getSchedule().blockingFirst()

        assertEquals(dataProvider.getSampleData().size, items.size)
        assertEquals(dataProvider.getSampleData()[0], items[0])
        assertEquals(dataProvider.getSampleData()[1], items[1])
    }

    @Test
    fun overwritesTables() {
        mediaDao.saveSchedule(dataProvider.getSampleData())

        mediaDao.saveSchedule(dataProvider.getSampleData().subList(0, 1))
        val items = mediaDao.getSchedule().blockingFirst()

        assertEquals(1, items.size)
        assertEquals(dataProvider.getSampleData()[0], items[0])
    }
}