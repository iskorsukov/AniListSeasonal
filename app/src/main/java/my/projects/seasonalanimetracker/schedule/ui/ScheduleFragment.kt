package my.projects.seasonalanimetracker.schedule.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_schedule.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import my.projects.seasonalanimetracker.schedule.ui.tabs.ScheduleTabViewPagerAdapter
import my.projects.seasonalanimetracker.schedule.viewmodel.ScheduleViewModel
import timber.log.Timber
import java.time.DayOfWeek

@AndroidEntryPoint
class ScheduleFragment: BaseFragment() {

    private val scheduleViewModel: ScheduleViewModel by viewModels()

    private var adapter: RecyclerView.Adapter<*>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_schedule
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleViewModel.updateSchedule() // TODO figure out a better update scheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleViewModel.scheduleLD().observe(viewLifecycleOwner) { scheduleVO ->

            val items = scheduleVO.scheduleItems()
            Timber.d("Showing ${items.values.flatten().size} items")

            if (adapter == null) {
                adapter = ScheduleTabViewPagerAdapter(this, items)
                pager.adapter = adapter
            } else {
                val diff = DiffUtil.calculateDiff(ScheduleDiffUtilCallback(items))
                diff.dispatchUpdatesTo(adapter!!)
            }

            TabLayoutMediator(tab_layout, pager) { tab, position ->
                val dayOfWeek = DayOfWeek.valueOf(items.keys.toList()[position].name)
                tab.text = dayOfWeek.name
            }.attach()
        }
    }
}


class ScheduleDiffUtilCallback(private val schedule: Map<DayOfWeek, List<ScheduleMediaItem>>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return DayOfWeek.values().size
    }

    override fun getNewListSize(): Int {
        return DayOfWeek.values().size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDayOfWeek = DayOfWeek.values()[oldItemPosition]
        val newDayOfWeek = DayOfWeek.values()[newItemPosition]

        val oldItem = schedule[oldDayOfWeek]!!
        val newItem = schedule[newDayOfWeek]!!

        return oldDayOfWeek == newDayOfWeek && oldItem.size == newItem.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDayOfWeek = DayOfWeek.values()[oldItemPosition]
        val newDayOfWeek = DayOfWeek.values()[newItemPosition]

        val oldItems = schedule[oldDayOfWeek]!!
        val newItems = schedule[newDayOfWeek]!!

        val sameSize = oldItems.size == newItems.size
        if (!sameSize || oldItems.zip(newItems).any { (first, second) -> first != second }) {
            return false
        }

        return true
    }
}