package my.projects.seasonalanimetracker.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import my.projects.seasonalanimetracker.BuildConfig
import my.projects.seasonalanimetracker.app.net.AuthInterceptor
import my.projects.seasonalanimetracker.notifications.domain.work.INotificationWorkManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.logging.Logger
import javax.inject.Inject

@HiltAndroidApp
class App: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun providesPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}