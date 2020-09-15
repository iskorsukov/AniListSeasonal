package my.projects.seasonalanimetracker.following

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert.assertTrue
import my.projects.seasonalanimetracker.following.domain.FollowingDataSource
import my.projects.seasonalanimetracker.following.domain.FollowingSeasonSource
import my.projects.seasonalanimetracker.following.domain.IFollowingLoader
import my.projects.seasonalanimetracker.following.domain.db.FollowingDAO
import my.projects.seasonalanimetracker.following.domain.mapper.db.FollowingDataToEntityMapper
import my.projects.seasonalanimetracker.following.domain.mapper.db.FollowingEntityToDataMapper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class TestFollowingDataSource {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockFollowingDao = Mockito.mock(FollowingDAO::class.java)
    private val mockFollowingLoader = Mockito.mock(IFollowingLoader::class.java)

    private val dataSource = FollowingDataSource(
        mockFollowingDao,
        mockFollowingLoader,
        FollowingDataToEntityMapper(),
        FollowingEntityToDataMapper(),
        FollowingSeasonSource()
    )

    @Test
    fun updatesFollowing() {
        Mockito.`when`(mockFollowingLoader.loadFollowing(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).thenReturn(
            Single.just(emptyList()))

        dataSource.updateFollowing().blockingAwait()

        Mockito.verify(mockFollowingLoader).loadFollowing(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        Mockito.verify(mockFollowingDao).saveFollowingItems(ArgumentMatchers.anyList())
    }

    @Test
    fun returnsStoredItems() {
        Mockito.`when`(mockFollowingDao.getFollowing()).thenReturn(Observable.just(emptyList()))

        val following = dataSource.getFollowing().blockingFirst()

        assertTrue(following.isEmpty())
        Mockito.verify(mockFollowingDao).getFollowing()
    }
}