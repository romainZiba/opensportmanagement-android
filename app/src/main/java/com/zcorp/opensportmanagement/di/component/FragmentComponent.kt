package com.zcorp.opensportmanagement.di.component


import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.di.module.FragmentModule
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventFragment
import com.zcorp.opensportmanagement.ui.main.fragments.messages.MessagesFragment
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(fragment: EventFragment)
    fun inject(fragment: MessagesFragment)
}
