package my.projects.seasonalanimetracker.main.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.common.ui.OnCharactersExpandClickListener
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.common.ui.OnStaffExpandClickListener
import my.projects.seasonalanimetracker.auth.viewobject.AuthStatus
import my.projects.seasonalanimetracker.auth.viewobject.IAuthVO
import my.projects.seasonalanimetracker.following.ui.FollowingFragment
import my.projects.seasonalanimetracker.main.viewmodel.IMainActivityViewModel
import my.projects.seasonalanimetracker.main.viewmodel.MainActivityViewModel
import my.projects.seasonalanimetracker.media.ui.MediaFragment
import my.projects.seasonalanimetracker.media.ui.item.character.CharactersFragment
import my.projects.seasonalanimetracker.media.ui.item.staff.StaffFragment
import my.projects.seasonalanimetracker.notifications.ui.NotificationsFragment
import my.projects.seasonalanimetracker.schedule.ui.ScheduleFragment

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), OnMediaItemClickListener, OnCharactersExpandClickListener, OnStaffExpandClickListener {

    private lateinit var toolbar: ActionBar

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        initBottomNavigation()
        bottomNavigation.selectedItemId = R.id.bottomMenuShowsSchedule
    }

    override fun onStart() {
        super.onStart()
        viewModel.authStatusLD().observe(this, Observer { authVO: IAuthVO ->
            if (authVO.authStatus() == AuthStatus.LOGGED_IN) {
                showBottomNavigation()
            } else {
                hideBottomNavigation()
            }
        })
    }

    private fun initBottomNavigation() {
        // TODO recycle fragments instead of recreating them, Navigation?
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomMenuNotifications -> {
                    toolbar.setTitle(R.string.title_notifications)
                    openFragment(NotificationsFragment())
                    true
                }
                R.id.bottomMenuShowsSchedule -> {
                    toolbar.setTitle(R.string.title_schedule)
                    openFragment(ScheduleFragment())
                    true
                }
                R.id.bottomNavigationShowsFollowing -> {
                    toolbar.setTitle(R.string.title_following)
                    openFragment(FollowingFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun showBottomNavigation() {
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        bottomNavigation.visibility = View.GONE
    }

    private fun openFragment(fragment: Fragment, onBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if (onBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onClickMediaItem(media: Media) {
        openFragment(MediaFragment.newInstance(media), true)
    }

    override fun showMoreCharacters(characters: List<MediaCharacter>) {
        openFragment(CharactersFragment.newInstance(characters), true)
    }

    override fun showMoreStaff(staff: List<MediaStaff>) {
        openFragment(StaffFragment.newInstance(staff), true)
    }
}