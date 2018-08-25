package com.zcorp.opensportmanagement.ui.eventdetails.fragments.members

import java.io.Serializable

/**
 * Created by romainz on 16/03/18.
 */
class EventMembersPresenter : IEventMembersPresenter {

    private var mView: IEventMembersView? = null

    override fun onAttach(view: IEventMembersView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}