package my.projects.seasonalanimetracker.auth.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.Completable
import io.reactivex.internal.operators.completable.CompletableFromAction
import javax.inject.Inject

interface IAuthInteractor {
    fun auth(authCode: String): Completable
    fun isAuthorized(): Boolean
    fun getToken(): String?
    fun removeToken()
}

class AuthInteractor @Inject constructor(
    private val authClient: IAuthClient,
    private val authDataSource: IAuthDataSource
): IAuthInteractor {

    override fun auth(authCode: String): Completable {
        return CompletableFromAction.fromAction {
            authDataSource.saveToken(authCode)
            Completable.complete()
        }
    }

    override fun isAuthorized(): Boolean {
        return authDataSource.isAuthorized()
    }

    override fun getToken(): String? {
        return authDataSource.getToken()
    }

    override fun removeToken() {
        authDataSource.deleteToken()
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthInteractorModule {
    @Binds
    abstract fun bindsAuthInteractor(authInteractor: AuthInteractor): IAuthInteractor
}