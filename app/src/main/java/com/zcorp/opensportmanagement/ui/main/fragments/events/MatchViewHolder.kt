package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.fragment_match_item.view.*

/**
 * Created by romainz on 03/02/18.
 */
class MatchViewHolder(private val mView: View, private val presenter: IEventsPresenter) : RecyclerView.ViewHolder(mView), IMatchViewRow {

    override fun setLocalTeamName(name: String) {
        mView.tv_local_name_match.text = name
    }

    override fun setDate(dateAsString: String) {
        mView.tv_date_match.text = dateAsString
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

    override fun setListener() {
        mView.setOnClickListener({
            presenter.onItemClicked(adapterPosition)
        })
    }
}
