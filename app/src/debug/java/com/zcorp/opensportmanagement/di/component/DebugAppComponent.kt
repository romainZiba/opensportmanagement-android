package com.zcorp.opensportmanagement.di.component

import com.zcorp.opensportmanagement.di.module.DebugApplicationModule
import com.zcorp.opensportmanagement.di.module.NetModule
import com.zcorp.opensportmanagement.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Singleton
@Component(modules = [DebugApplicationModule::class, NetModule::class, ViewModelModule::class])
interface DebugAppComponent : AppComponent
