package com.zcorp.opensportmanagement.di.component

import android.app.Application
import com.zcorp.opensportmanagement.di.module.IntegrationApplicationModule


/**
 * Created by romainz on 09/03/18.
 */
class ComponentFactory {
    companion object {
        fun create(context: Application): AppComponent {
            return DaggerIntegrationAppComponent.builder()
                    .integrationApplicationModule(IntegrationApplicationModule(context))
                    .build()
        }
    }

}