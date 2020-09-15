package my.projects.seasonalanimetracker.following

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.following.domain.FollowingLoader
import my.projects.seasonalanimetracker.following.domain.IFollowingQueryClient
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class TestFollowingLoader {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockQueryClient = Mockito.mock(IFollowingQueryClient::class.java)
    private val followingLoader = FollowingLoader(mockQueryClient)

    @Test
    fun loadsFollowing() {
        Mockito.`when`(mockQueryClient.getPagesCount()).thenReturn(Single.just(1))
        Mockito.`when`(mockQueryClient.getPage(ArgumentMatchers.anyInt())).thenReturn(Single.just(emptyList()))

        val following = followingLoader.loadFollowing("", 1).blockingGet()

        assertEquals(0, following.size)
        Mockito.verify(mockQueryClient).getPage(ArgumentMatchers.anyInt())
    }
}