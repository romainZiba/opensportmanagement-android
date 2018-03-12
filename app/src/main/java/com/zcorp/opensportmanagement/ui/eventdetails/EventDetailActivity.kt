package com.zcorp.opensportmanagement.ui.eventdetails

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import kotlinx.android.synthetic.main.activity_event_detail.*


class EventDetailActivity : AppCompatActivity(), IEventDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        setSupportActionBar(toolbar_event_details)
        event_detail_subscription_fab.setOnClickListener { view ->
            ThemedSnackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_event_detail, menu)
        return true
    }
}
