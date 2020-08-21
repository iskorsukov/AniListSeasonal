package my.projects.seasonalanimetracker.auth.domain

import android.content.Context
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.net.SimpleOkHttpClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

interface IAuthClient {
    fun loadToken(authCode: String): Single<String>
}

class AuthClient @Inject constructor(
    @ApplicationContext private val context: Context,
    @SimpleOkHttpClient private val okHttpClient: OkHttpClient
): IAuthClient {

    override fun loadToken(authCode: String): Single<String> {
        return Single.fromCallable {
            okHttpClient.newCall(getRequest(authCode)).execute().use { response ->
                return@use response.body!!.string()
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun getRequest(authCode: String): Request {
        return Request.Builder()
            .url("https://anilist.co/api/v2/oauth/token")
            .post(getJSONBody(authCode))
            .build()
    }

    private fun getJSONBody(authCode: String): RequestBody {
        val authObject = OAuthBodyObject(
            context.getString(R.string.client_id),
            authCode
        )
        val json = Gson().toJson(authObject)
        return json.toRequestBody()
    }

    private data class OAuthBodyObject(
        val client_id: String,
        val code: String,
        val grant_type: String = "authorization_code"
    )
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthClientModule {
    @Binds
    abstract fun bindsAuthClient(authClient: AuthClient): IAuthClient
}