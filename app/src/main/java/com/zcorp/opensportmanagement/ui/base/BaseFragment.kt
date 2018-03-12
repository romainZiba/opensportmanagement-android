package com.zcorp.opensportmanagement.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.di.component.DaggerFragmentComponent
import com.zcorp.opensportmanagement.di.component.FragmentComponent
import com.zcorp.opensportmanagement.di.module.FragmentModule

/**
 * Created by romainz on 16/02/18.
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var mFragmentComponent: FragmentComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(MyApplication.appComponent)
                .fragmentModule(FragmentModule(this))
                .build()
    }
}