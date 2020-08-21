package my.projects.seasonalanimetracker.notifications.viewobject

import androidx.paging.PagedList
import my.projects.seasonalanimetracker.notifications.data.NotificationMediaItem

interface INotificationsVO {
    fun notificationItems(): PagedList<NotificationMediaItem>
}

class PagedNotificationsVO(private val items: PagedList<NotificationMediaItem>): INotificationsVO {
    override fun notificationItems(): PagedList<NotificationMediaItem> {
        return items
    }
}