package com.zcorp.opensportmanagement.ui

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.zcorp.opensportmanagement.R


/**
 * Created by romainz on 26/02/18.
 */
class ThemedSnackbar {
    companion object {
        fun make(view: View, text: CharSequence, duration: Int): Snackbar {
            val snackbar = Snackbar.make(view, text, duration)
            snackbar.setActionTextColor(ContextCompat.getColor(view.context, R.color.light_green_500))
            snackbar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
                    .setTextColor(ContextCompat.getColor(view.context, R.color.white))
            return snackbar
        }

        fun make(view: View, resId: Int, duration: Int): Snackbar {
            return make(view, view.resources.getText(resId), duration)
        }
    }
}