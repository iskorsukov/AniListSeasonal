package my.projects.seasonalanimetracker.following.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.projects.seasonalanimetracker.following.domain.IFollowingDataSource
import my.projects.seasonalanimetracker.following.viewobject.FollowingVO
import my.projects.seasonalanimetracker.following.viewobject.IFollowingVO

abstract class IFollowingViewModel: ViewModel() {
    abstract fun followingLD(): LiveData<IFollowingVO>
    abstract fun updateFollowing()
}

class FollowingViewModel @ViewModelInject constructor(
    private val followingDataSource: IFollowingDataSource
): IFollowingViewModel() {

    private val followingLD: LiveData<IFollowingVO> by lazy {
        MutableLiveData<IFollowingVO>().also { liveData ->
            followingDataSource.getFollowing().subscribe {
                liveData.postValue(FollowingVO(it))
            }
        }
    }

    override fun followingLD(): LiveData<IFollowingVO> {
        return followingLD
    }

    override fun updateFollowing() {
        followingDataSource.updateFollowing().subscribe()
    }
}