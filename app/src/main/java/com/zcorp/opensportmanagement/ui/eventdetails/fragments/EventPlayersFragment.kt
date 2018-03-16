package com.zcorp.opensportmanagement.ui.eventdetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.ui.DividerDecoration
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_event_details_players.view.*
import javax.inject.Inject

/**
 * Created by romainz on 15/03/18.
 */
class EventPlayersFragment : BaseFragment() {

    @Inject
    lateinit var dataManager: IDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_details_players, container, false)
        val recyclerView = view.rv_event_participant_list
        val dividerItemDecoration = DividerDecoration(recyclerView.context)
        recyclerView.addItemDecoration(dividerItemDecoration)

        dataManager.getMatch(1).subscribe { match ->
            recyclerView.adapter = TeamMemberRecyclerAdapter(
                    this.activity!!.baseContext, match.presentTeamMembers.toMutableList(), match.absentTeamMembers.toMutableList())
        }
        return view
    }

}