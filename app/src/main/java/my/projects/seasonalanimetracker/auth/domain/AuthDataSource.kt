package my.projects.seasonalanimetracker.auth.domain

import android.content.SharedPreferences
import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import timber.log.Timber
import javax.inject.Inject

interface IAuthDataSource {
    fun isAuthorized(): Boolean
    fun getToken(): String?
    fun saveToken(token: String)
    fun deleteToken()
}

class AuthDataSource @Inject constructor (private val prefs: SharedPreferences): IAuthDataSource {

    companion object {
        const val AUTH_TOKEN_KEY = "auth_token"
    }

    override fun isAuthorized(): Boolean {
        return prefs.contains(AUTH_TOKEN_KEY)
    }

    override fun getToken(): String? {
        Timber.d("fetching token ${prefs.getString(AUTH_TOKEN_KEY, null)}")
        return prefs.getString(AUTH_TOKEN_KEY, null)
    }

    override fun saveToken(token: String) {
        Timber.d("saving token $token")
        prefs.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    override fun deleteToken() {
        prefs.edit().remove(AUTH_TOKEN_KEY).apply()
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthDataSourceModule {
    @Binds
    abstract fun bindsAuthDataSource(authDataSource: AuthDataSource): IAuthDataSource
}