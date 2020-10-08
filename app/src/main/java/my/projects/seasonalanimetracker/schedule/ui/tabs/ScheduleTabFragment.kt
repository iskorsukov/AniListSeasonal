package my.projects.seasonalanimetracker.schedule.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_schedule_tab.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.ui.media.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.common.ui.media.status.OnModifyMediaStatusClickListener
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.ui.item.ScheduleRecyclerViewAdapter

class ScheduleTabFragment: Fragment() {

    companion object {
        const val ARG_ITEMS = "items"
        const val ARG_AUTH = "auth"

        fun newInstance(items: List<ScheduleMediaItem>, isAuth: Boolean): ScheduleTabFragment {
            val fragment =
                ScheduleTabFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(ARG_ITEMS, ArrayList<ScheduleMediaItem>(items))
                putBoolean(ARG_AUTH, isAuth)
            }
            return fragment
        }
    }

    private lateinit var adapter: ScheduleRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ScheduleRecyclerViewAdapter(
            requireParentFragment() as OnMediaItemClickListener,
            requireParentFragment() as OnModifyMediaStatusClickListener,
            requireArguments().getBoolean(ARG_AUTH)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter

        val items = requireArguments().getSerializable(ARG_ITEMS) as ArrayList<ScheduleMediaItem>
        adapter.submitList(items)
    }
}