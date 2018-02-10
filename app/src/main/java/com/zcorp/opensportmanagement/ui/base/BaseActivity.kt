package com.zcorp.opensportmanagement.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.di.component.ActivityComponent
import com.zcorp.opensportmanagement.di.component.DaggerActivityComponent
import com.zcorp.opensportmanagement.di.module.ActivityModule

/**
 * Created by romainz on 10/02/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(MyApplication.appComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

}