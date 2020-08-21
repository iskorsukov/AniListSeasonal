package my.projects.seasonalanimetracker.schedule.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_schedule_tab.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.ui.item.ScheduleRecyclerViewAdapter
import java.time.DayOfWeek

class ScheduleTabFragment: Fragment() {

    companion object {
        const val ARG_ITEMS = "items"

        fun newInstance(items: List<ScheduleMediaItem>): ScheduleTabFragment {
            val fragment =
                ScheduleTabFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(ARG_ITEMS, ArrayList<ScheduleMediaItem>(items))
            }
            return fragment
        }
    }

    private val adapter = ScheduleRecyclerViewAdapter()

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