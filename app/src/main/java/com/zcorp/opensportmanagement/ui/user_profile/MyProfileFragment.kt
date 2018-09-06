package com.zcorp.opensportmanagement.ui.user_profile

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity
import com.zcorp.opensportmanagement.repository.Status
import com.zcorp.opensportmanagement.ui.FailedEvent
import com.zcorp.opensportmanagement.ui.LoadingEvent
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.utils.DatePickerFragment
import com.zcorp.opensportmanagement.utils.datetime.dateFormatter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.fragment_my_profile.my_profile_toolbar
import kotlinx.android.synthetic.main.my_profile_content.et_profile_birthdate
import kotlinx.android.synthetic.main.my_profile_content.et_profile_email
import kotlinx.android.synthetic.main.my_profile_content.et_profile_first_name
import kotlinx.android.synthetic.main.my_profile_content.et_profile_licence_number
import kotlinx.android.synthetic.main.my_profile_content.et_profile_name
import kotlinx.android.synthetic.main.my_profile_content.et_profile_phone_number
import kotlinx.android.synthetic.main.my_profile_content.pb_update_profile
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

class MyProfileFragment : BaseFragment() {

    companion object {
        private const val DATE_PICKER = "datePicker"
    }

    private val datePicker = DatePickerFragment.newInstance()
    private val mSchedulerProvider: SchedulerProvider by inject()
    private val viewModel: MyProfileViewModel by viewModel()
    private var teamMember: TeamMemberEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(my_profile_toolbar as Toolbar)

        viewModel.profileLiveData.observe(this, Observer { resource ->
            when (resource?.status) {
                Status.SUCCESS -> displayInformation(resource.data?.firstOrNull())
                Status.LOADING -> displayInformation(resource.data?.firstOrNull())
                Status.ERROR -> Toast.makeText(context, getString(R.string.error_get_profile), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.updateEvents.observe(this, Observer { event ->
            when (event) {
                is LoadingEvent -> pb_update_profile.visibility = View.VISIBLE
                is FailedEvent -> {
                    pb_update_profile.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.error_update_profile), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    pb_update_profile.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.success_update_profile), Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getTeamMemberInfo()

        datePicker.observable.subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { date ->
                    val formattedDate = dateFormatter().format(date)
                    et_profile_birthdate.setText(formattedDate)
                }

        et_profile_birthdate.setOnClickListener {
            datePicker.showDialog(activity!!.supportFragmentManager, DATE_PICKER)
        }
    }

    private fun displayInformation(entity: TeamMemberEntity?) {
        entity?.let {
            teamMember = it
            et_profile_first_name.setText(entity.firstName)
            et_profile_name.setText(entity.lastName)
            et_profile_email.setText(entity.email)
            et_profile_licence_number.setText(entity.licenceNumber)
            et_profile_phone_number.setText(entity.phoneNumber)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.my_profile_menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.update_profile -> {
                viewModel.updateTeamMember(
                        firstName = et_profile_first_name.text.toString(),
                        lastName = et_profile_name.text.toString(),
                        email = et_profile_email.text.toString(),
                        licenceNumber = et_profile_licence_number.text.toString(),
                        phoneNumber = et_profile_phone_number.text.toString())
                true
            }
            else -> false
        }
    }
}
