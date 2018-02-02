package com.zcorp.opensportmanagement.screens.teams

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User
import kotlinx.android.synthetic.main.activity_main.*

class TeamsActivity : AppCompatActivity(), TeamsView {

    private val presenter: TeamsPresenter = TeamsPresenterImpl(this, TeamsModelImpl())


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                presenter.getTeams(User("Roger", "Roget", mutableSetOf(1, 2)))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun showTeams(teams: List<Team>) {
        message.setText(R.string.title_home)
    }

    override fun showNetworkError() {
        message.setText(R.string.network_error)
    }
}
