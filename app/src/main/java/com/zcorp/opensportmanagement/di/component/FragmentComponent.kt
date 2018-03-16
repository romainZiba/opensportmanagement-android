package com.zcorp.opensportmanagement.di.component

import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.di.module.FragmentModule
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.EventInformationFragment
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.EventPlayersFragment
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsFragment
import dagger.Component

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun inject(fragment: EventsFragment)
    fun inject(fragment: ConversationsFragment)
    fun inject(fragment: EventPlayersFragment)
    fun inject(fragment: EventInformationFragment)
}
