package my.projects.seasonalanimetracker.following.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import my.projects.seasonalanimetracker.type.MediaSeason
import java.util.*
import javax.inject.Inject

interface IFollowingSeasonSource {
    fun getCurrentSeason(): String
    fun getCurrentYear(): Int
}

class FollowingSeasonSource @Inject constructor(): IFollowingSeasonSource {

    override fun getCurrentSeason(): String {
        val seasonIndex = Calendar.getInstance().get(Calendar.MONTH) / 4
        return when (seasonIndex) {
            0 -> MediaSeason.SPRING.name
            1 -> MediaSeason.SUMMER.name
            2 -> MediaSeason.FALL.name
            3 -> MediaSeason.WINTER.name
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FollowingSeasonSourceModule {
    @Binds
    abstract fun bindsFollowingSeasonSource(followingSeasonSource: FollowingSeasonSource): IFollowingSeasonSource
}