package com.zcorp.opensportmanagement.di.component

import com.zcorp.opensportmanagement.di.module.DebugApplicationModule
import com.zcorp.opensportmanagement.di.module.NetModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Singleton
@Component(modules = [(DebugApplicationModule::class), (NetModule::class)])
interface DebugAppComponent : AppComponent
