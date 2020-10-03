package my.projects.seasonalanimetracker.following.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import my.projects.seasonalanimetracker.following.data.FollowingMediaItem
import my.projects.seasonalanimetracker.following.data.MediaListAction
import my.projects.seasonalanimetracker.following.domain.IFollowingDataSource
import my.projects.seasonalanimetracker.following.viewobject.FollowingVO
import my.projects.seasonalanimetracker.following.viewobject.IFollowingVO

abstract class IFollowingViewModel: ViewModel() {
    abstract fun followingLD(): LiveData<IFollowingVO>
    abstract fun updateFollowing()
    abstract fun processFollowingAction(action: MediaListAction, item: FollowingMediaItem)
}

class FollowingViewModel @ViewModelInject constructor(
    private val followingDataSource: IFollowingDataSource
): IFollowingViewModel() {

    private val followingLD: LiveData<IFollowingVO> by lazy {
        MutableLiveData<IFollowingVO>().also { liveData ->
            followingDataSource.getFollowing().subscribe {
                liveData.postValue(FollowingVO(it, followingDataSource.getCurrentSeason()))
            }
        }
    }

    override fun followingLD(): LiveData<IFollowingVO> {
        return followingLD
    }

    override fun updateFollowing() {
        followingDataSource.updateFollowing().subscribe()
    }

    override fun processFollowingAction(action: MediaListAction, item: FollowingMediaItem) {
        if (action == MediaListAction.REMOVE) {
            removeFromFollowing(item)
        }
    }

    private fun removeFromFollowing(item: FollowingMediaItem) {
        followingDataSource.removeFromFollowing(item).subscribe()
    }
}

@Module
@InstallIn(FragmentComponent::class)
abstract class FollowingViewModelModule {
    @Binds
    abstract fun bindsFollowingViewModel(followingViewModel: FollowingViewModel): IFollowingViewModel
}