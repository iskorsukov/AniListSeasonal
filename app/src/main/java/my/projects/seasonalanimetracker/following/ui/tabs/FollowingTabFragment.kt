package my.projects.seasonalanimetracker.following.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_following_tab.*
import kotlinx.android.synthetic.main.fragment_schedule_tab.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.ui.NoScrollLinearLayoutManager
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.ui.item.FollowingRecyclerViewAdapter
import my.projects.seasonalanimetracker.following.ui.item.OnModifyFollowingClickListener
import my.projects.seasonalanimetracker.schedule.ui.tabs.ScheduleTabFragment
import my.projects.seasonalanimetracker.type.MediaListStatus

class FollowingTabFragment: BaseFragment() {

    companion object {
        const val ARG_ITEMS = "items"

        fun newInstance(following: List<FollowingMediaItem>): FollowingTabFragment {
            val fragment =
                FollowingTabFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(ARG_ITEMS, ArrayList<FollowingMediaItem>(following))
            }
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_following_tab
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerViews()

        val items = requireArguments().getSerializable(ScheduleTabFragment.ARG_ITEMS) as ArrayList<FollowingMediaItem>
        showData(items)
    }

    private fun configureRecyclerViews() {
        current_recycler_view.layoutManager = NoScrollLinearLayoutManager(requireContext())
        current_recycler_view.adapter = FollowingRecyclerViewAdapter(
            requireParentFragment() as OnMediaItemClickListener,
            requireParentFragment() as OnModifyFollowingClickListener
        )

        planning_recycler_view.layoutManager = NoScrollLinearLayoutManager(requireContext())
        planning_recycler_view.adapter = FollowingRecyclerViewAdapter(
            requireParentFragment() as OnMediaItemClickListener,
            requireParentFragment() as OnModifyFollowingClickListener
        )

        completed_recycler_view.layoutManager = NoScrollLinearLayoutManager(requireContext())
        completed_recycler_view.adapter = FollowingRecyclerViewAdapter(
            requireParentFragment() as OnMediaItemClickListener,
            requireParentFragment() as OnModifyFollowingClickListener
        )

        paused_recycler_view.layoutManager = NoScrollLinearLayoutManager(requireContext())
        paused_recycler_view.adapter = FollowingRecyclerViewAdapter(
            requireParentFragment() as OnMediaItemClickListener,
            requireParentFragment() as OnModifyFollowingClickListener
        )

        dropped_recycler_view.layoutManager = NoScrollLinearLayoutManager(requireContext())
        dropped_recycler_view.adapter = FollowingRecyclerViewAdapter(
            requireParentFragment() as OnMediaItemClickListener,
            requireParentFragment() as OnModifyFollowingClickListener
        )
    }

    private fun showData(items: List<FollowingMediaItem>) {
        val currentItems = items.filter { item -> item.status == MediaListStatus.CURRENT.name }
        current_label.visibility = if (currentItems.isEmpty()) View.GONE else View.VISIBLE
        current_recycler_view.visibility = if (currentItems.isEmpty()) View.GONE else View.VISIBLE
        (current_recycler_view.adapter as FollowingRecyclerViewAdapter).submitList(currentItems)

        val planningItems = items.filter { item -> item.status == MediaListStatus.PLANNING.name }
        planning_label.visibility = if (planningItems.isEmpty()) View.GONE else View.VISIBLE
        planning_recycler_view.visibility = if (planningItems.isEmpty()) View.GONE else View.VISIBLE
        (planning_recycler_view.adapter as FollowingRecyclerViewAdapter).submitList(planningItems)

        val completedItems = items.filter { item -> item.status == MediaListStatus.COMPLETED.name }
        completed_label.visibility = if (completedItems.isEmpty()) View.GONE else View.VISIBLE
        completed_recycler_view.visibility = if (completedItems.isEmpty()) View.GONE else View.VISIBLE
        (completed_recycler_view.adapter as FollowingRecyclerViewAdapter).submitList(completedItems)

        val pausedItems = items.filter { item -> item.status == MediaListStatus.PAUSED.name }
        paused_label.visibility = if (pausedItems.isEmpty()) View.GONE else View.VISIBLE
        paused_recycler_view.visibility = if (pausedItems.isEmpty()) View.GONE else View.VISIBLE
        (paused_recycler_view.adapter as FollowingRecyclerViewAdapter).submitList(pausedItems)

        val droppedItems = items.filter { item -> item.status == MediaListStatus.DROPPED.name }
        dropped_label.visibility = if (droppedItems.isEmpty()) View.GONE else View.VISIBLE
        dropped_recycler_view.visibility = if (droppedItems.isEmpty()) View.GONE else View.VISIBLE
        (dropped_recycler_view.adapter as FollowingRecyclerViewAdapter).submitList(droppedItems)
    }
}