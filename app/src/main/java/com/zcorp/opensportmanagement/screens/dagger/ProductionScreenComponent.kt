package com.zcorp.opensportmanagement.screens.dagger

import com.zcorp.opensportmanagement.application.builder.AppComponent
import com.zcorp.opensportmanagement.screens.main.MainActivity
import dagger.Component

/**
 * Created by romainz on 08/02/18.
 */
@ScreenScope
@Component(modules = arrayOf(ScreenModule::class), dependencies = arrayOf(AppComponent::class))
interface ProductionScreenComponent {
    fun inject(target: MainActivity)
}