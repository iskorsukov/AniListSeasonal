package my.projects.seasonalanimetracker.app.common.data.media

import my.projects.seasonalanimetracker.app.testing.Mockable
import my.projects.seasonalanimetracker.app.common.data.characters.MediaCharacter
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.common.data.studios.MediaStudio
import java.io.Serializable

@Mockable
data class Media(
    val id: Long,
    val type: String,
    val format: String,
    val titleRomaji: String?,
    val titleNative: String?,
    val titleEnglish: String?,
    val description: String?,
    val coverImageUrl: String?,
    val bannerImageUrl: String?,
    val episodes: Int?,
    val genres: String?,
    val averageScore: Double?,
    val meanScore: Double?,
    val character: List<MediaCharacter>,
    val staff: List<MediaStaff>,
    val studios: List<MediaStudio>,
    val siteUrl: String
): Serializable