package my.projects.seasonalanimetracker.notifications.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notifications.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.notifications.ui.item.NotificationsRecyclerViewPagedAdapter
import my.projects.seasonalanimetracker.notifications.viewmodel.NotificationViewModel

@AndroidEntryPoint
class NotificationsFragment: BaseFragment(), OnMediaItemClickListener {

    private val viewModel: NotificationViewModel by viewModels()

    private lateinit var adapter: NotificationsRecyclerViewPagedAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_notifications
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NotificationsRecyclerViewPagedAdapter(this)
        viewModel.updateNotifications() // TODO figure out a better update scheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        viewModel.notificationsLD().observe(viewLifecycleOwner) { notificationsVO ->
            adapter.submitList(notificationsVO.notificationItems())
        }
    }

    override fun onClickMediaItem(media: Media) {
        findNavController().navigate(NotificationsFragmentDirections.actionNotificationsFragmentToMediaFragment(media))
    }
}