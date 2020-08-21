package my.projects.seasonalanimetracker.app.net

import my.projects.seasonalanimetracker.auth.domain.IAuthDataSource
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val authDataSource: IAuthDataSource): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        Timber.d("Auth intercept")
        authDataSource.getToken()?.let {
            Timber.d("Adding token $it")
            request = request.newBuilder().header("Authorization", "Bearer $it").build()
        }
        return chain.proceed(request)
    }
}