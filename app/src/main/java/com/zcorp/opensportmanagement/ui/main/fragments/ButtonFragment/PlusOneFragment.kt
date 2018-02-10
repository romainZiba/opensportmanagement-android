package com.zcorp.opensportmanagement.ui.main.fragments.ButtonFragment


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import kotlinx.android.synthetic.main.fragment_plus_one.*


/**
 * A fragment with a Google +1 button.
 */
class PlusOneFragment : Fragment() {
    // The URL to +1.  Must be a valid URL.
    private val PLUS_ONE_URL = "http://developer.android.com"


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_plus_one, container, false)
    }

    override fun onResume() {
        super.onResume()
        // Refresh the state of the +1 button each time the activity receives focus.
        plus_one_button.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE)
    }

    companion object {

        // The request code must be 0 or greater.
        private val PLUS_ONE_REQUEST_CODE = 0
    }
}
