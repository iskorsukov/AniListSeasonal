package my.projects.seasonalanimetracker.notifications.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.db.dao.NotificationsDAO
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationDataToEntityMapper
import my.projects.seasonalanimetracker.notifications.domain.mapper.db.NotificationEntityToDataMapper
import timber.log.Timber
import javax.inject.Inject

interface INotificationsDataSource {
    fun getNotifications(): LiveData<PagedList<NotificationMediaItem>>
    fun updateNotifications(): Completable
}

class NotificationsDataSource @Inject constructor(
    private val notificationsLoader: INotificationsLoader,
    private val notificationsDAO: NotificationsDAO,
    private val entityToDataMapper: NotificationEntityToDataMapper,
    private val dataToEntityMapper: NotificationDataToEntityMapper
): INotificationsDataSource {

    override fun getNotifications(): LiveData<PagedList<NotificationMediaItem>> {
        return notificationsDAO.getNotifications().map {
            entityToDataMapper.map(it)
        }.toLiveData(pageSize = 20)
    }

    override fun updateNotifications(): Completable {
        return notificationsLoader.loadNotifications().flatMapCompletable {
            storeNotifications(it)
        }.subscribeOn(Schedulers.io())
    }

    private fun storeNotifications(notifications: List<NotificationMediaItem>): Completable {
        Timber.d("Storing ${notifications.size} notifications")
        return Completable.fromAction {
            notificationsDAO.saveNotifications(notifications.map { dataToEntityMapper.map(it) })
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NotificationDataSourceModule {
    @Binds
    abstract fun bindsNotificationDataSource(notificationsDataSource: NotificationsDataSource): INotificationsDataSource
}