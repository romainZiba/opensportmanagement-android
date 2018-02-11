package com.zcorp.opensportmanagement.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.ActivityContext
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.login.ILoginPresenter
import com.zcorp.opensportmanagement.ui.login.LoginPresenter
import com.zcorp.opensportmanagement.ui.main.IMainPresenter
import com.zcorp.opensportmanagement.ui.main.MainPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventFragment
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val mActivity: AppCompatActivity): BaseContextModule() {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    @Provides
    internal fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    @PerActivity
    internal fun provideLoginPresenter(dataManager: IDataManager): ILoginPresenter {
        return LoginPresenter(dataManager)
    }

    @Provides
    @PerActivity
    internal fun provideMainPresenter(): IMainPresenter {
        return MainPresenter()
    }

    @Provides
    @PerActivity
    internal fun provideEventsFragment(): EventFragment {
        return EventFragment()
    }
}
