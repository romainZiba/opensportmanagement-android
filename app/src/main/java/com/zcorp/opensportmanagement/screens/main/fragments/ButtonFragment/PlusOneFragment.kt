package com.zcorp.opensportmanagement.screens.main.fragments.ButtonFragment


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.plus.PlusOneButton
import com.zcorp.opensportmanagement.R


/**
 * A fragment with a Google +1 button.
 */
class PlusOneFragment : Fragment() {
    // The URL to +1.  Must be a valid URL.
    private val PLUS_ONE_URL = "http://developer.android.com"
    private var mPlusOneButton: PlusOneButton? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_plus_one, container, false)

        //Find the +1 button
        mPlusOneButton = view.findViewById(R.id.plus_one_button)

        return view
    }

    override fun onResume() {
        super.onResume()

        // Refresh the state of the +1 button each time the activity receives focus.
        mPlusOneButton!!.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE)
    }

    companion object {

        // The request code must be 0 or greater.
        private val PLUS_ONE_REQUEST_CODE = 0
    }


}// Required empty public constructor
