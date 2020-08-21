package my.projects.seasonalanimetracker.schedule.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import my.projects.seasonalanimetracker.schedule.data.ScheduleMediaItem
import java.time.DayOfWeek

class ScheduleTabViewPagerAdapter(fragmentManager: FragmentManager, schedule: Map<DayOfWeek, List<ScheduleMediaItem>>):
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val data = LinkedHashMap<DayOfWeek, List<ScheduleMediaItem>>().also {
        it.putAll(schedule)
    }

    fun updateData(items: Map<DayOfWeek, List<ScheduleMediaItem>>) {
        data.clear()
        data.putAll(items)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.keys.size
    }

    override fun getItem(position: Int): Fragment {
        val dayOfWeek = DayOfWeek.valueOf(data.keys.toList()[position].name)
        return ScheduleTabFragment.newInstance(data[dayOfWeek]!!) // TODO resolve nullability in a better way
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data.keys.toList()[position].name
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}