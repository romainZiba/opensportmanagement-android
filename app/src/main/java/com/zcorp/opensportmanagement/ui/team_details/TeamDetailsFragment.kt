package com.zcorp.opensportmanagement.ui.team_details

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.ui.base.BaseListFragment
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.remote.dto.Role
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_team_details.rv_team_members
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TeamDetailsFragment : BaseListFragment<TeamMember>() {

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: SectionedRecyclerViewAdapter

    override val recyclerView: RecyclerView?
        get() = activity?.findViewById(R.id.rv_team_members)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = SectionedRecyclerViewAdapter()
        mLayoutManager = LinearLayoutManager(activity)

        rv_team_members.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }

        viewModel.memberStates.observe(this, Observer { state ->
            when (state) {
                is State.Success -> {
                    mItems = state.data
                    if (mItems.isNotEmpty()) submitList(mItems)
                }
            }
        })

        viewModel.selectedTeamId.observe(this, Observer { teamId ->
            teamId?.let {
                viewModel.getTeamMembers(it, true)
            }
        })
    }

    private fun submitList(members: List<TeamMember>) {
        val coaches = members.filter { it.roles.contains(Role.COACH) }
        val players = members.filter { it.roles.contains(Role.PLAYER) && it.roles.contains(Role.COACH).not() }
        mAdapter.removeAllSections()
        if (coaches.isNotEmpty()) mAdapter.addSection(MemberSection("Coaches", coaches))
        if (players.isNotEmpty()) mAdapter.addSection(MemberSection("Players", players))
        mAdapter.notifyDataSetChanged()
    }


    override fun buildIntent(selectedItem: Int): Intent {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSelectedItems(items: List<TeamMember>) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}