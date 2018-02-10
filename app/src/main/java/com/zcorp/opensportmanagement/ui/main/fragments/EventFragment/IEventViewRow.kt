package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

/**
 * Created by romainz on 03/02/18.
 */
interface IEventViewRow {
    fun setLocalTeamName(name: String)
    fun setDate(dateAsString: String)
    fun setDescription(description: String)
    fun setListener()
}