package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.graphics.drawable.Drawable

/**
 * Created by romainz on 03/02/18.
 */
interface IEventViewRow {
    fun setDate(dateAsString: String)
    fun setDescription(description: String)
    fun setListener()
}