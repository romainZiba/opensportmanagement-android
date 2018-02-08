package com.zcorp.opensportmanagement.screens.dagger

import com.zcorp.opensportmanagement.application.builder.AppComponent
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventFragment
import dagger.Component

/**
 * Created by romainz on 08/02/18.
 */
@MainScope
@Component(modules = arrayOf(MainModule::class), dependencies = arrayOf(AppComponent::class))
interface MainComponent {
    fun inject(view: EventFragment)
}