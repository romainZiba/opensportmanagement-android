package com.zcorp.opensportmanagement.ui.events.adapter

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.zcorp.opensportmanagement.R
import kotlinx.android.synthetic.main.match_description_layout.view.iv_local_team_match
import kotlinx.android.synthetic.main.match_description_layout.view.iv_visitor_match
import kotlinx.android.synthetic.main.match_description_layout.view.tv_local_name_match
import kotlinx.android.synthetic.main.match_description_layout.view.tv_visitor_name_match
import kotlinx.android.synthetic.main.rv_item_match.view.tv_event_date
import kotlinx.android.synthetic.main.rv_item_match.view.tv_event_place

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

class MatchViewHolder(private val mView: View) : BaseViewHolder(mView) {

    override fun setDate(dateAsString: String) {
        mView.tv_event_date.text = dateAsString
    }

    fun setLocalTeamName(name: String) {
        mView.tv_local_name_match.text = name
    }

    fun setVisitorTeamName(name: String) {
        mView.tv_visitor_name_match.text = name
    }

    fun setLocalTeamImage(drawable: Drawable) {
        mView.iv_local_team_match.setImageDrawable(drawable)
    }

    fun setVisitorTeamImage(drawable: Drawable) {
        mView.iv_visitor_match.setImageDrawable(drawable)
    }

    override fun setPlace(place: String) {
        mView.tv_event_place.text = place
    }
}

class LoadingViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
    val progressBar = mView.findViewById<ProgressBar>(R.id.pb_events_rv_item)
}
