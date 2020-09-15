package my.projects.seasonalanimetracker.following

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Completable
import io.reactivex.Observable
import junit.framework.Assert.assertTrue
import my.projects.seasonalanimetracker.following.domain.IFollowingDataSource
import my.projects.seasonalanimetracker.following.viewmodel.FollowingViewModel
import my.projects.seasonalanimetracker.following.viewobject.IFollowingVO
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TestFollowingViewModel {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockFollowingDataSource = Mockito.mock(IFollowingDataSource::class.java)

    init {
        Mockito.`when`(mockFollowingDataSource.getFollowing()).thenReturn(Observable.just(emptyList()))
    }

    private val viewModel = FollowingViewModel(mockFollowingDataSource)

    @Test
    fun returnsFollowingVO() {
        val followingLD = viewModel.followingLD()

        var followingVO: IFollowingVO? = null
        while (followingVO == null) {
            followingVO = followingLD.value
            TimeUnit.SECONDS.sleep(1)
        }

        assertTrue(followingVO.getItems().isEmpty())
    }

    @Test
    fun updatesFollowing() {
        Mockito.`when`(mockFollowingDataSource.updateFollowing()).thenReturn(Completable.complete())

        viewModel.updateFollowing()

        Mockito.verify(mockFollowingDataSource).updateFollowing()
    }
}