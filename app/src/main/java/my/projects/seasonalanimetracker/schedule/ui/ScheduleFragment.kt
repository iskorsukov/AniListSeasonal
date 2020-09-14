package my.projects.seasonalanimetracker.schedule.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_schedule.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.schedule.ui.tabs.ScheduleTabViewPagerAdapter
import my.projects.seasonalanimetracker.schedule.viewmodel.ScheduleViewModel
import timber.log.Timber

@AndroidEntryPoint
class ScheduleFragment: BaseFragment(), OnMediaItemClickListener {

    private val scheduleViewModel: ScheduleViewModel by viewModels()

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

            if (pager.adapter == null) {
                pager.adapter = ScheduleTabViewPagerAdapter(childFragmentManager, items)
                pager.setCurrentItem(1, false)
            } else {
                (pager.adapter!! as ScheduleTabViewPagerAdapter).updateData(items)
            }

            tab_layout.setupWithViewPager(pager)
        }
    }

    override fun onClickMediaItem(media: Media) {
        findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToMediaFragment(media))
    }
}
