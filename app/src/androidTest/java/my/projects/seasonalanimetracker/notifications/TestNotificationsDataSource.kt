package my.projects.seasonalanimetracker.notifications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Single
import junit.framework.Assert.assertTrue
import my.projects.seasonalanimetracker.db.dao.NotificationsDAO
import my.projects.seasonalanimetracker.db.data.notification.DBNotificationItemEntity
import my.projects.seasonalanimetracker.notifications.domain.INotificationsLoader
import my.projects.seasonalanimetracker.notifications.domain.NotificationsDataSource
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationDataToEntityMapper
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationEntityToDataMapper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class TestNotificationsDataSource {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockNotificationsLoader = Mockito.mock(INotificationsLoader::class.java)
    private val mockNotificationsDao = Mockito.mock(NotificationsDAO::class.java)
    private val notificationsDataSource = NotificationsDataSource(
        mockNotificationsLoader,
        mockNotificationsDao,
        NotificationEntityToDataMapper(),
        NotificationDataToEntityMapper()
    )

    @Test
    fun loadsAndStoresNotifications() {
        Mockito.`when`(mockNotificationsLoader.loadNotifications()).thenReturn(Single.just(emptyList()))

        notificationsDataSource.updateNotifications().blockingAwait()

        Mockito.verify(mockNotificationsLoader).loadNotifications()
        Mockito.verify(mockNotificationsDao).saveNotifications(ArgumentMatchers.anyList())
    }

    @Test
    fun returnsStoredNotifications() {
        Mockito.`when`(mockNotificationsDao.getPagedNotifications()).thenReturn(object : DataSource.Factory<Int, DBNotificationItemEntity>(){
            override fun create(): DataSource<Int, DBNotificationItemEntity> {
                return object : PositionalDataSource<DBNotificationItemEntity>() {
                    override fun loadInitial(
                        params: LoadInitialParams,
                        callback: LoadInitialCallback<DBNotificationItemEntity>
                    ) {
                        callback.onResult(emptyList(), 0, 0)
                    }

                    override fun loadRange(
                        params: LoadRangeParams,
                        callback: LoadRangeCallback<DBNotificationItemEntity>
                    ) {
                        callback.onResult(emptyList())
                    }

                }
            }

        })

        val notifications = notificationsDataSource.getNotifications().blockingFirst()

        assertTrue(notifications.isEmpty())
        Mockito.verify(mockNotificationsDao).getPagedNotifications()
    }



}