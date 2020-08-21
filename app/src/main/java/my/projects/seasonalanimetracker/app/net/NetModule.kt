package my.projects.seasonalanimetracker.app.net

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SimpleOkHttpClient

@Module
@InstallIn(ActivityRetainedComponent::class)
class NetModule {

    @Provides
    @AuthInterceptOkHttpClient
    fun providesAuthInterceptHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val client = OkHttpClient()
        val logging = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message);
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return client.newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @SimpleOkHttpClient
    fun providesHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message);
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun providesApolloClient(@AuthInterceptOkHttpClient client: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .okHttpClient(client)
            .serverUrl("https://graphql.anilist.co")
            .build()
    }
}