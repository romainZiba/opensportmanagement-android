package com.zcorp.opensportmanagement.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zcorp.opensportmanagement.di.ViewModelKey
import com.zcorp.opensportmanagement.ui.login.LoginViewModel
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsViewModel
import com.zcorp.opensportmanagement.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    internal abstract fun bindEventsViewModel(eventsViewModel: EventsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}