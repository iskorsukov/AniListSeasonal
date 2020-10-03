package my.projects.seasonalanimetracker.following.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_following.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.data.MediaListAction
import my.projects.seasonalanimetracker.following.ui.item.OnModifyFollowingClickListener
import my.projects.seasonalanimetracker.following.ui.tabs.FollowingTabViewPagerAdapter
import my.projects.seasonalanimetracker.following.viewmodel.FollowingViewModel
import timber.log.Timber

@AndroidEntryPoint
class FollowingFragment: BaseFragment(), OnMediaItemClickListener, OnModifyFollowingClickListener {

    private val viewModel: FollowingViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_following
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateFollowing()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.followingLD().observe(viewLifecycleOwner) { followingVO ->
            Timber.i("Showing ${followingVO.getItems().size} entities")

            if (pager.adapter == null) {
                pager.adapter = FollowingTabViewPagerAdapter(
                    childFragmentManager,
                    followingVO.getItems(),
                    followingVO.getSeason(),
                    getString(R.string.all)
                )
            } else {
                (pager.adapter as FollowingTabViewPagerAdapter).updateData(followingVO.getItems())
            }

            tab_layout.setupWithViewPager(pager)
        }
    }

    override fun onClickMediaItem(media: Media) {
        findNavController().navigate(FollowingFragmentDirections.actionFollowingFragmentToMediaFragment(media))
    }

    override fun onModifyClick(item: FollowingMediaItem) {
        showFollowingActionsDialog(item)
    }

    private fun showFollowingActionsDialog(item: FollowingMediaItem) {
        val labels = resources.getStringArray(R.array.media_list_action_labels)
        val actions = resources.getStringArray(R.array.media_list_actions)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.select_action)
            .setItems(labels) { dialog, pos ->
                dialog?.dismiss()
                val action = MediaListAction.valueOf(actions[pos].toUpperCase())
                if (action == MediaListAction.REMOVE) {
                    showRemoveDialog(item)
                } else {
                    viewModel.processFollowingAction(
                        action,
                        item
                    )
                }
            }
            .setCancelable(true)
        dialog.show()
    }

    private fun showRemoveDialog(item: FollowingMediaItem) {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage(R.string.remove_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.processFollowingAction(
                    MediaListAction.REMOVE,
                    item
                )
                dialog?.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog?.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}