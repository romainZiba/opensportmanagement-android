package com.zcorp.opensportmanagement.ui.eventdetails.fragments.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.DividerDecoration
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailsActivity
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.adapter.TeamMemberRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_event_details_players.view.*

/**
 * Created by romainz on 15/03/18.
 */
class EventMembersFragment : BaseFragment(), IEventMembersView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_details_players, container, false)
        val recyclerView = view.rv_event_participant_list
        val dividerItemDecoration = DividerDecoration(recyclerView.context)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = TeamMemberRecyclerAdapter(this.context!!, (mActivity as EventDetailsActivity).mPresenter)
        return view
    }
}