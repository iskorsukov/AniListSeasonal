package my.projects.seasonalanimetracker.app.common.ui

import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff

interface OnStaffExpandClickListener {
    fun showMoreStaff(staff: List<MediaStaff>)
}