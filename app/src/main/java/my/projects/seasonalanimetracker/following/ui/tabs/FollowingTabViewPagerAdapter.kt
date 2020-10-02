package my.projects.seasonalanimetracker.following.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import timber.log.Timber

class FollowingTabViewPagerAdapter(
    fragmentManager: FragmentManager,
    following: List<FollowingMediaItem>,
    private val season: Pair<String, Int>,
    private val allLabel: String)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val data = mutableListOf<FollowingMediaItem>().also {
        it.addAll(following)
    }

    override fun getCount(): Int {
        return 2
    }

    fun updateData(following: List<FollowingMediaItem>) {
        Timber.d("Updating data with ${following.size} items")
        data.clear()
        data.addAll(following)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            FollowingTabFragment.newInstance(getCurrentSeasonItems())
        } else {
            FollowingTabFragment.newInstance(data)
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            "${season.first} ${season.second}"
        } else {
            allLabel
        }
    }

    private fun getCurrentSeasonItems(): List<FollowingMediaItem> {
        val list = mutableListOf<FollowingMediaItem>()
        for (item in data) {
            item.media.let {
                if (it.season != null && it.seasonYear != null && it.season == season.first && it.seasonYear == season.second) {
                    list.add(item)
                }
            }
        }
        return list
    }
}