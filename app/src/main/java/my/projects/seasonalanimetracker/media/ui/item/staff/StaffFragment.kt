package my.projects.seasonalanimetracker.media.ui.item.staff

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_staff.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment

class StaffFragment: BaseFragment() {

    companion object {
        fun newInstance(staff: List<MediaStaff>): StaffFragment {
            return StaffFragment().also {
                it.arguments = Bundle().also {
                    it.putSerializable("staff", ArrayList(staff))
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_staff
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        staff_recycler.layoutManager = LinearLayoutManager(context)
        staff_recycler.adapter = StaffRecyclerViewAdapter().also { adapter ->
            (arguments?.getSerializable("staff") as List<MediaStaff>?)?.let {
                adapter.submitList(it)
            }
        }
    }
}