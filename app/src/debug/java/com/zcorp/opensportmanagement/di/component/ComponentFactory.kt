package com.zcorp.opensportmanagement.di.component

import android.app.Application
import com.zcorp.opensportmanagement.di.module.DebugApplicationModule


/**
 * Created by romainz on 09/03/18.
 */
class ComponentFactory {
    companion object {
        fun create(context: Application): AppComponent {
            return DaggerDebugAppComponent.builder()
                    .debugApplicationModule(DebugApplicationModule(context))
                    .build()
        }
    }

}