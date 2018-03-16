package com.zcorp.opensportmanagement.di.component

import com.zcorp.opensportmanagement.di.module.IntegrationApplicationModule
import com.zcorp.opensportmanagement.di.module.NetModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Singleton
@Component(modules = [(IntegrationApplicationModule::class), (NetModule::class)])
interface IntegrationAppComponent : AppComponent