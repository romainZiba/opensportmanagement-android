package com.zcorp.opensportmanagement.screens.test

import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventViewRow
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventsPresenter

/**
 * Created by romainz on 08/02/18.
 */
class FakeEventsPresenter: EventsPresenter {
    override fun onBindEventRowViewAtPosition(position: Int, holder: EventViewRow) {
    }

    override fun getEvents() {
    }

    override fun getEventsCount(): Int {
        return 0
    }

    override fun finish() {
    }

    override fun onItemClicked(adapterPosition: Int) {
    }
}