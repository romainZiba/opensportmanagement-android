package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.view.View
import kotlinx.android.synthetic.main.rv_item_event.view.*

/**
 * Created by romainz on 03/02/18.
 */
class EventViewHolder(private val mView: View) : BaseViewHolder(mView), IEventViewHolder {

    override fun setDate(dateAsString: String) {
        mView.tv_date_event.text = dateAsString
    }

    override fun setDescription(description: String) {
        mView.tv_description_event.text = description
    }
}
