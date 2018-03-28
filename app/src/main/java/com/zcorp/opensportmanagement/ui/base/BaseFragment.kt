package com.zcorp.opensportmanagement.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.di.component.DaggerFragmentComponent
import com.zcorp.opensportmanagement.di.component.FragmentComponent
import com.zcorp.opensportmanagement.di.module.FragmentModule

/**
 * Created by romainz on 16/02/18.
 */
abstract class BaseFragment : Fragment(), IBaseView {

    protected lateinit var mFragmentComponent: FragmentComponent
    protected var mActivity: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(MyApplication.appComponent)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.mActivity = null
    }

    override fun closeSoftKeyboard() {
        mActivity?.closeSoftKeyboard()
    }
}