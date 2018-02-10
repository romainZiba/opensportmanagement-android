package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.zcorp.opensportmanagement.R

/**
 * Created by romainz on 03/02/18.
 */
class EventViewHolder(private val mView: View, private val presenter: IEventsPresenter) : RecyclerView.ViewHolder(mView), IEventViewRow {

    private val mLocalTeamNameView: TextView = mView.findViewById(R.id.local_team_name)
    private val mDate: TextView = mView.findViewById(R.id.date)

    override fun setLocalTeamName(name: String) {
        mLocalTeamNameView.text = name
    }

    override fun setDate(dateAsString: String) {
        mDate.text = dateAsString
    }

    override fun setDescription(description: String) {
    }

    override fun setListener() {
        mView.setOnClickListener({
            presenter.onItemClicked(adapterPosition)
        })
    }
}
