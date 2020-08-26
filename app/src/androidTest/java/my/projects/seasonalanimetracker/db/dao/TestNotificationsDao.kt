package my.projects.seasonalanimetracker.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.db.DBTestDataProvider
import my.projects.seasonalanimetracker.db.MediaDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestNotificationsDao {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MediaDatabase
    private lateinit var notificationsDAO: NotificationsDAO
    private val dataProvider = DBTestDataProvider()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MediaDatabase::class.java).allowMainThreadQueries().build()
        notificationsDAO = db.notificationsDao()
    }

    @Test
    fun insertsAndReturns() {
        notificationsDAO.saveNotifications(dataProvider.getNotificationsSampleData())

        val items = notificationsDAO.getNotifications().blockingGet()

        assertEquals(dataProvider.getNotificationsSampleData().size, items.size)
        assertEquals(dataProvider.getNotificationsSampleData()[0], items[0])
        assertEquals(dataProvider.getNotificationsSampleData()[1], items[1])
    }

    @Test
    fun doesNotOverwriteNotificationsData() {
        notificationsDAO.saveNotifications(dataProvider.getNotificationsSampleData())

        notificationsDAO.saveNotifications(dataProvider.getNotificationsSampleData().subList(0, 1))
        val items = notificationsDAO.getNotifications().blockingGet()

        assertEquals(2, items.size)
        assertEquals(dataProvider.getNotificationsSampleData()[0], items[0])
        assertEquals(dataProvider.getNotificationsSampleData()[1], items[1])
    }

    @Test
    fun deleteDoesNotCascade() {
        notificationsDAO.saveNotifications(dataProvider.getNotificationsSampleData())

        notificationsDAO.clearNotifications()

        val items = notificationsDAO.getNotifications().blockingGet()
        assertEquals(0, items.size)
        assertEquals(db.query("select * from shows", emptyArray()).count, 2)
    }
}