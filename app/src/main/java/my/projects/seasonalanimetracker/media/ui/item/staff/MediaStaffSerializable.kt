package my.projects.seasonalanimetracker.media.ui.item.staff

import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import java.io.Serializable

data class MediaStaffSerializable(
    val staff: ArrayList<MediaStaff>
): Serializable