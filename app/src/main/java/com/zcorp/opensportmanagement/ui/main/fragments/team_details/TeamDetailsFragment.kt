package com.zcorp.opensportmanagement.ui.main.fragments.team_details

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imminov.equisure.view.base.BaseListFragment
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.repository.State
import kotlinx.android.synthetic.main.fragment_team_details.fab_add_team_member
import kotlinx.android.synthetic.main.fragment_team_details.rv_team_members
import org.koin.android.architecture.ext.viewModel

class TeamDetailsFragment : BaseListFragment<TeamMember>() {

    private val viewModel: TeamDetailsViewModel by viewModel()
    private lateinit var mTeamMembersAdapter: TeamMembersAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override val recyclerView: RecyclerView?
        get() = activity?.findViewById(R.id.rv_team_members)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_add_team_member.setOnClickListener {
        }

        mTeamMembersAdapter = TeamMembersAdapter(listOf(), this)
        mLayoutManager = LinearLayoutManager(activity)

        rv_team_members.apply {
            adapter = mTeamMembersAdapter
            layoutManager = mLayoutManager
            val dividerItemDecoration = DividerItemDecoration(this.context, mLayoutManager.orientation)
            this.addItemDecoration(dividerItemDecoration)
        }

        viewModel.states.observe(this, Observer { state ->
            when (state) {
                is State.Success -> {
                    mItems = state.data
                    rv_team_members.adapter = TeamMembersAdapter(state.data, this)
                }
            }
        })
        viewModel.getTeamMembers()
    }

    override fun buildIntent(selectedItem: Int): Intent {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSelectedItems(items: List<TeamMember>) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}