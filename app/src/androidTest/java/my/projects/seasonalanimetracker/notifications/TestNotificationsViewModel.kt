package my.projects.seasonalanimetracker.notifications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.paging.toObservable
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Completable
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import my.projects.seasonalanimetracker.notifications.domain.INotificationsDataSource
import my.projects.seasonalanimetracker.notifications.viewmodel.NotificationViewModel
import my.projects.seasonalanimetracker.notifications.viewobject.INotificationsVO
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TestNotificationsViewModel {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockNotificationsDataSource = Mockito.mock(INotificationsDataSource::class.java)

    init {
        Mockito.`when`(mockNotificationsDataSource.getNotifications())
            .thenReturn(getDataSourceFactory().toObservable(pageSize = 20))
    }

    private val viewModel = NotificationViewModel(mockNotificationsDataSource)

    @Test
    fun returnsNotificationVO() {
        var viewObject: INotificationsVO? = null
        while (viewObject == null) {
            viewObject = viewModel.notificationsLD().value

            TimeUnit.SECONDS.sleep(1)
        }

        assertEquals(0, viewObject.notificationItems().size)
        Mockito.verify(mockNotificationsDataSource).getNotifications()
    }

    @Test
    fun updatesNotifications() {
        Mockito.`when`(mockNotificationsDataSource.updateNotifications()).thenReturn(Completable.complete())

        viewModel.updateNotifications()

        Mockito.verify(mockNotificationsDataSource).updateNotifications()
    }

    private fun getDataSourceFactory(): DataSource.Factory<Int, NotificationMediaItem> {
        return object : DataSource.Factory<Int, NotificationMediaItem>(){
            override fun create(): DataSource<Int, NotificationMediaItem> {
                return object : PositionalDataSource<NotificationMediaItem>() {
                    override fun loadInitial(
                        params: LoadInitialParams,
                        callback: LoadInitialCallback<NotificationMediaItem>
                    ) {
                        callback.onResult(emptyList(), 0, 0)
                    }

                    override fun loadRange(
                        params: LoadRangeParams,
                        callback: LoadRangeCallback<NotificationMediaItem>
                    ) {
                        callback.onResult(emptyList())
                    }

                }
            }
        }
    }
}