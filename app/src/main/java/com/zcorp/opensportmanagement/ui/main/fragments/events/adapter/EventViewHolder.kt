package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.view.View
import com.zcorp.opensportmanagement.ui.main.fragments.events.IEventsPresenter
import kotlinx.android.synthetic.main.fragment_event_item.view.*

/**
 * Created by romainz on 03/02/18.
 */
class EventViewHolder(
        private val mView: View,
        presenter: IEventsPresenter) : BaseViewHolder(mView, presenter), IEventViewHolder {

    override fun setDescription(description: String) {
        mView.tv_description_event.text = description
    }
}
