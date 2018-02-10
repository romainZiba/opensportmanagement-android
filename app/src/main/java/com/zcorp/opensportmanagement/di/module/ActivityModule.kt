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
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.EventFragment
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.EventsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.IEventsPresenter
import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

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
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
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
    internal fun provideEventsPresenter(dataManager: IDataManager): IEventsPresenter {
        return EventsPresenter(dataManager)
    }

    @Provides
    @PerActivity
    internal fun provideEventsFragment(): EventFragment {
        return EventFragment()
    }
}
