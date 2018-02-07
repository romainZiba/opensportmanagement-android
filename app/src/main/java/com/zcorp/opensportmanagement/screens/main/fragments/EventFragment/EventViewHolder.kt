package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.zcorp.opensportmanagement.R

/**
 * Created by romainz on 03/02/18.
 */
class EventViewHolder(private val mView: View, private val presenter: EventsPresenter) : RecyclerView.ViewHolder(mView), EventViewRow {

    private val mNameView: TextView = mView.findViewById<TextView>(R.id.name) as TextView
    private val mDescriptionView: TextView = mView.findViewById<TextView>(R.id.description) as TextView

    override fun setName(name: String) {
        mNameView.text = name
    }

    override fun setDescription(description: String) {
        mDescriptionView.text = description
    }

    override fun toString(): String {
        return super.toString() + " '" + mDescriptionView.text + "'"
    }

    override fun setListener() {
        mView.setOnClickListener({
            presenter.onItemClicked(adapterPosition)
        })
    }
}
