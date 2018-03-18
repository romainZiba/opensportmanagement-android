package com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.rv_item_participant_header.view.*

/**
 * Created by romainz on 12/02/18.
 */
class HeaderViewHolder(private val mView: View, val viewType: Int) : RecyclerView.ViewHolder(mView) {

    fun setTitle(title: String) {
        mView.tv_item_participant_header_title.text = title
    }
}