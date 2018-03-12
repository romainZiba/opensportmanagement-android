package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.graphics.drawable.Drawable
import android.view.View
import com.zcorp.opensportmanagement.ui.main.fragments.events.IEventsPresenter
import kotlinx.android.synthetic.main.match_description_layout.view.*

/**
 * Created by romainz on 03/02/18.
 */
class MatchViewHolder(
        private val mView: View,
        presenter: IEventsPresenter) : BaseViewHolder(mView, presenter), IMatchViewHolder {

    override fun setDescription(description: String) {

    }

    override fun setLocalTeamName(name: String) {
        mView.tv_local_name_match.text = name
    }

    override fun setVisitorTeamName(name: String) {
        mView.tv_visitor_name_match.text = name
    }

    override fun setLocalTeamImage(drawable: Drawable) {
        mView.iv_local_team_match.setImageDrawable(drawable)
    }

    override fun setVisitorTeamImage(drawable: Drawable) {
        mView.iv_visitor_match.setImageDrawable(drawable)
    }
}
