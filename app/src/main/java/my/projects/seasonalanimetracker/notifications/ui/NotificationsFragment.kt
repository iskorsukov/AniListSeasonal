package my.projects.seasonalanimetracker.notifications.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notifications.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.notifications.ui.item.NotificationsRecyclerViewAdapter
import my.projects.seasonalanimetracker.notifications.ui.item.NotificationsRecyclerViewPagedAdapter
import my.projects.seasonalanimetracker.notifications.viewmodel.INotificationViewModel
import my.projects.seasonalanimetracker.notifications.viewmodel.NotificationViewModel

@AndroidEntryPoint
class NotificationsFragment: BaseFragment() {

    private val viewModel: NotificationViewModel by viewModels()

    private val adapter = NotificationsRecyclerViewPagedAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_notifications
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateNotifications() // TODO figure out a better update scheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        viewModel.notificationsLD().observe(viewLifecycleOwner, Observer { notificationsVO ->
            adapter.submitList(notificationsVO.notificationItems())
        })
    }
}