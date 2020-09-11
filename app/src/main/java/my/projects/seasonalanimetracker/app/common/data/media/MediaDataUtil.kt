package my.projects.seasonalanimetracker.app.common.data.media

object MediaDataUtil {
    fun studiosToString(media: Media): String {
        return media.studios.joinToString(separator = ", ") {mediaStudio -> mediaStudio.studio.name }
    }
}