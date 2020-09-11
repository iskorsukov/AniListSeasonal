package my.projects.seasonalanimetracker.following.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_following.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.app.common.ui.OnMediaItemClickListener
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment
import my.projects.seasonalanimetracker.following.ui.item.FollowingRecyclerViewAdapter
import my.projects.seasonalanimetracker.following.viewmodel.FollowingViewModel
import timber.log.Timber

@AndroidEntryPoint
class FollowingFragment: BaseFragment(), OnMediaItemClickListener {

    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var adapter: FollowingRecyclerViewAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_following
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FollowingRecyclerViewAdapter(this)
        viewModel.updateFollowing()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        viewModel.followingLD().observe(viewLifecycleOwner) { followingVO ->
            Timber.i("Showing ${followingVO.getItems().size} entities")
            adapter.submitList(followingVO.getItems())
        }
    }

    override fun onClickMediaItem(media: Media) {
        findNavController().navigate(FollowingFragmentDirections.actionFollowingFragmentToMediaFragment(media))
    }
}