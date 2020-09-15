package my.projects.seasonalanimetracker.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.db.DBTestDataProvider
import my.projects.seasonalanimetracker.db.MediaDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestFollowingDao {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, MediaDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    private val followingDao = db.followingDao()

    private val dataProvider = DBTestDataProvider()

    @Test
    fun insertsAndReturns() {
        followingDao.saveFollowingItems(dataProvider.getFollowingSampleData())

        val items = followingDao.getFollowing().blockingFirst()

        assertEquals(dataProvider.getFollowingSampleData(), items)
    }

    @Test
    fun overwritesFollowingData() {
        followingDao.saveFollowingItems(listOf(dataProvider.getFollowingSampleData().first()))
        followingDao.saveFollowingItems(listOf(dataProvider.getFollowingSampleData().last()))

        val items = followingDao.getFollowing().blockingFirst()

        assertEquals(dataProvider.getFollowingSampleData().subList(1, 2), items)
    }

    @Test
    fun deleteDoesNotCascade() {
        followingDao.saveFollowingItems(dataProvider.getFollowingSampleData())
        followingDao.clearFollowingItems()

        val items = followingDao.getFollowing().blockingFirst()

        assertEquals(0, items.size)
        assertEquals(db.query("select * from shows", emptyArray()).count, 2)
    }
}