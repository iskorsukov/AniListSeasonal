package my.projects.seasonalanimetracker.notifications.domain

import androidx.paging.PagedList
import androidx.paging.toObservable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
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
    fun getNotifications(): Observable<PagedList<NotificationMediaItem>>
    fun updateNotifications(): Completable
}

class NotificationsDataSource @Inject constructor(
    private val notificationsLoader: INotificationsLoader,
    private val notificationsDAO: NotificationsDAO,
    private val entityToDataMapper: NotificationEntityToDataMapper,
    private val dataToEntityMapper: NotificationDataToEntityMapper
): INotificationsDataSource {

    override fun getNotifications(): Observable<PagedList<NotificationMediaItem>> {
        return notificationsDAO.getPagedNotifications().map {
            entityToDataMapper.map(it)
        }.toObservable(pageSize = 20)
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
@InstallIn(ApplicationComponent::class)
abstract class NotificationDataSourceModule {
    @Binds
    abstract fun bindsNotificationDataSource(notificationsDataSource: NotificationsDataSource): INotificationsDataSource
}