package my.projects.seasonalanimetracker.notifications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import my.projects.seasonalanimetracker.notifications.domain.INotificationsQueryClient
import my.projects.seasonalanimetracker.notifications.domain.NotificationsLoader
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class TestNotificationsLoader {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockQueryClient = Mockito.mock(INotificationsQueryClient::class.java)
    private val notificationsLoader = NotificationsLoader(mockQueryClient)

    @Test
    fun loadsUnreadNotificationsCount() {
        Mockito.`when`(mockQueryClient.getUnreadNotificationsCount()).thenReturn(Single.just(5))

        val count = notificationsLoader.loadUnreadNotificationsCount().blockingGet()

        assertEquals(5, count)
        Mockito.verify(mockQueryClient).getUnreadNotificationsCount()
    }

    @Test
    fun loadsNotifications() {
        Mockito.`when`(mockQueryClient.getPage()).thenReturn(Single.just(emptyList()))

        val notifications = notificationsLoader.loadNotifications().blockingGet()

        assertTrue(notifications.isEmpty())
        Mockito.verify(mockQueryClient).getPage()
    }

}