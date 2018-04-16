package com.zcorp.opensportmanagement.di.module

import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.utils.DatePickerFragment
import com.zcorp.opensportmanagement.ui.utils.DayOfWeekPickerFragment
import com.zcorp.opensportmanagement.ui.utils.TimePickerFragment
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import com.zcorp.opensportmanagement.utils.stomp.StompClientProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by romainz on 10/02/18.
 */
@Module
abstract class BaseContextModule {
    @Provides
    @PerActivity
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @PerActivity
    internal fun provideStompClientProvider(): IStompClientProvider {
        return StompClientProvider()
    }

    @Provides
    @PerActivity
    internal fun provideDatePicker(): DatePickerFragment {
        return DatePickerFragment()
    }

    @Provides
    @PerActivity
    internal fun provideTimePicker(): TimePickerFragment {
        return TimePickerFragment()
    }

    @Provides
    @PerActivity
    internal fun provideDaysPicker(): DayOfWeekPickerFragment {
        return DayOfWeekPickerFragment()
    }
}