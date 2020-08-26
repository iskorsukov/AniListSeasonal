package my.projects.seasonalanimetracker.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import my.projects.seasonalanimetracker.auth.domain.IAuthDataSource
import my.projects.seasonalanimetracker.auth.viewobject.AuthStatus
import my.projects.seasonalanimetracker.auth.viewobject.AuthVO
import my.projects.seasonalanimetracker.auth.viewobject.IAuthVO
import my.projects.seasonalanimetracker.notifications.domain.work.INotificationWorkManager
import javax.inject.Inject

abstract class IMainActivityViewModel: ViewModel() {

    abstract fun authStatusLD(): LiveData<IAuthVO>
}

class MainActivityViewModel @ViewModelInject constructor(
    private val authDataSource: IAuthDataSource,
    private val notificationWorkManager: INotificationWorkManager
): IMainActivityViewModel() {

    private val authStatusLD = MutableLiveData<IAuthVO>().also {
        if (authDataSource.isAuthorized()) {
            it.value = AuthVO(AuthStatus.LOGGED_IN)
            notificationWorkManager.startNotificationCheckWork()
        } else {
            it.value = AuthVO(AuthStatus.NOT_LOGGED_IN)
        }
    }

    override fun authStatusLD(): LiveData<IAuthVO> {
        return authStatusLD
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityViewModelModule {
    @Binds
    abstract fun bindsMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): IMainActivityViewModel
}