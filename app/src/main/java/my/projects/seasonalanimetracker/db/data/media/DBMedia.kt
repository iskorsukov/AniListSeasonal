package my.projects.seasonalanimetracker.db.data.media

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class DBMedia(
    @PrimaryKey val id: Long,
    val type: String,
    val format: String,
    @Embedded val title: MediaTitle?,
    val season: String?,
    val seasonYear: Int?,
    val description: String?,
    val coverImageUrl: String?,
    val bannerImageUrl: String?,
    val episodes: Int?,
    val genres: String?,
    val averageScore: Double?,
    val meanScore: Double?,
    val siteUrl: String,
    val userStatus: String?
)

data class MediaTitle(
    val romaji: String?,
    val nativeT: String?,
    val english: String?
)