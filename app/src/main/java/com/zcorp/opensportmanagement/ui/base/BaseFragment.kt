package com.zcorp.opensportmanagement.ui.base

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created by romainz on 16/02/18.
 */
abstract class BaseFragment : Fragment(), IBaseView {

    protected var mActivity: BaseActivity? = null

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