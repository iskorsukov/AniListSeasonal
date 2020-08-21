package my.projects.seasonalanimetracker.schedule.ui.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.time.DayOfWeek

class ScheduleTabViewPagerAdapter(fragment: Fragment, private val schedule: Map<DayOfWeek, List<ScheduleMediaItem>>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return schedule.keys.size
    }

    override fun createFragment(position: Int): Fragment {
        val dayOfWeek = DayOfWeek.valueOf(schedule.keys.toList()[position].name)
        return ScheduleTabFragment.newInstance(schedule[dayOfWeek]!!) // TODO resolve nullability in a better way
    }
}