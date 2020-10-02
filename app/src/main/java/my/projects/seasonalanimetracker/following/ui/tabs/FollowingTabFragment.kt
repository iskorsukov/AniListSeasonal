package my.projects.seasonalanimetracker.following.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_schedule_tab.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.ui.item.FollowingRecyclerViewAdapter
import my.projects.seasonalanimetracker.following.ui.item.OnRemoveFollowingClickListener
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.ui.tabs.ScheduleTabFragment

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

    private lateinit var adapter: FollowingRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FollowingRecyclerViewAdapter(requireParentFragment() as OnMediaItemClickListener, requireParentFragment() as OnRemoveFollowingClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter

        val items = requireArguments().getSerializable(ScheduleTabFragment.ARG_ITEMS) as ArrayList<FollowingMediaItem>
        adapter.submitList(items)
    }
}