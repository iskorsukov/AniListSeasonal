package my.projects.seasonalanimetracker.main.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import my.projects.seasonalanimetracker.NavGraphDirections
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
import my.projects.seasonalanimetracker.notifications.ui.NotificationsFragmentDirections
import my.projects.seasonalanimetracker.schedule.ui.ScheduleFragment

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

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
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomMenuNotifications -> {
                    toolbar.setTitle(R.string.title_notifications)
                    getNavController().navigate(NavGraphDirections.actionGlobalNotificationsFragment())
                    true
                }
                R.id.bottomMenuShowsSchedule -> {
                    toolbar.setTitle(R.string.title_schedule)
                    getNavController().navigate(NavGraphDirections.actionGlobalScheduleFragment())
                    true
                }
                R.id.bottomNavigationShowsFollowing -> {
                    toolbar.setTitle(R.string.title_following)
                    getNavController().navigate(NavGraphDirections.actionGlobalFollowingFragment())
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

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}