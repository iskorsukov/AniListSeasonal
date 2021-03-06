package my.projects.seasonalanimetracker.auth.viewmodel

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.reactivex.disposables.CompositeDisposable
import my.projects.seasonalanimetracker.auth.domain.IAuthDataSource
import my.projects.seasonalanimetracker.auth.viewobject.AuthStatus
import my.projects.seasonalanimetracker.auth.viewobject.AuthVO
import my.projects.seasonalanimetracker.auth.viewobject.IAuthVO

abstract class IAuthViewModel: ViewModel() {
    abstract fun handleLoginComplete(data: Uri)
    abstract fun authStatusLD(): LiveData<IAuthVO>
}

class AuthViewModel @ViewModelInject constructor (
    private val authDataSource: IAuthDataSource
): IAuthViewModel() {

    private val authDisposable = CompositeDisposable()

    private val authStatusLD = MutableLiveData<IAuthVO>().also {
        if (authDataSource.isAuthorized()) {
            it.value = AuthVO(AuthStatus.LOGGED_IN)
        } else {
            it.value = AuthVO(AuthStatus.NOT_LOGGED_IN)
        }
    }

    override fun authStatusLD(): LiveData<IAuthVO> {
        return authStatusLD
    }

    override fun handleLoginComplete(data: Uri) {
        data.fragment?.let {
            val authCode = it.split("&").first().split("=").component2()
            authDataSource.saveToken(authCode)
            authStatusLD.postValue(AuthVO(AuthStatus.LOGGED_IN))
        }
    }

    override fun onCleared() {
        super.onCleared()
        authDisposable.dispose()
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthViewModelModule {
    @Binds
    abstract fun bindsAuthViewModel(authViewModel: AuthViewModel): IAuthViewModel
}