package my.projects.seasonalanimetracker.app.common.ui.media

import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff

interface OnStaffExpandClickListener {
    fun showMoreStaff(staff: List<MediaStaff>)
}