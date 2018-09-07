package com.zcorp.opensportmanagement.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by romainz on 16/02/18.
 */
abstract class BaseFragment : Fragment() {

    protected var mActivity: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
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
}