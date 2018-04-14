package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.graphics.drawable.Drawable
import android.view.View
import kotlinx.android.synthetic.main.match_description_layout.view.*
import kotlinx.android.synthetic.main.rv_item_match.view.*

/**
 * Created by romainz on 03/02/18.
 */
class MatchViewHolder(private val mView: View) : BaseViewHolder(mView) {

    override fun setDate(dateAsString: String) {
        mView.tv_date_event.text = dateAsString
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
}
