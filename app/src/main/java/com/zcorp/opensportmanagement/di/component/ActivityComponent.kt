package com.zcorp.opensportmanagement.di.component

import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.di.module.ActivityModule
import com.zcorp.opensportmanagement.ui.eventcreation.EventCreationActivity
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailsActivity
import com.zcorp.opensportmanagement.ui.login.LoginActivity
import com.zcorp.opensportmanagement.ui.main.MainActivity
import com.zcorp.opensportmanagement.ui.messages.MessagesActivity
import dagger.Component

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: MessagesActivity)
    fun inject(activity: EventDetailsActivity)
    fun inject(activity: EventCreationActivity)
}
