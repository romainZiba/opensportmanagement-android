package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.graphics.drawable.Drawable

/**
 * Created by romainz on 03/02/18.
 */
interface IMatchViewHolder: IEventViewHolder {
    fun setLocalTeamName(name: String)
    fun setVisitorTeamName(name: String)
    fun setLocalTeamImage(drawable: Drawable)
    fun setVisitorTeamImage(drawable: Drawable)
}