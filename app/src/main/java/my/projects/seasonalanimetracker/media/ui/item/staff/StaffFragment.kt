package my.projects.seasonalanimetracker.media.ui.item.staff

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_staff.*
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.staff.MediaStaff
import my.projects.seasonalanimetracker.app.ui.fragment.BaseFragment

class StaffFragment: BaseFragment() {

    private val args by navArgs<StaffFragmentArgs>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_staff
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staff = args.staffList.staff

        staff_recycler.layoutManager = LinearLayoutManager(context)
        staff_recycler.adapter = StaffRecyclerViewAdapter().also { adapter ->
            adapter.submitList(staff)
        }
    }
}