package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.view.View
import kotlinx.android.synthetic.main.rv_item_event.view.*

/**
 * Created by romainz on 03/02/18.
 */
class EventViewHolder(private val mView: View) : BaseViewHolder(mView) {
    override fun setDate(dateAsString: String) {
        mView.tv_event_date.text = dateAsString
    }

    override fun setPlace(place: String) {
        mView.tv_event_place.text = place
    }
}
