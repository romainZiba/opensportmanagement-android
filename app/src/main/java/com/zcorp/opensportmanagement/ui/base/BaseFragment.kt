package com.zcorp.opensportmanagement.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by romainz on 16/02/18.
 */
abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}