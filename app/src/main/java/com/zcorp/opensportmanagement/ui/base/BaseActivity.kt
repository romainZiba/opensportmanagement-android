package com.zcorp.opensportmanagement.ui.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

/**
 * Created by romainz on 10/02/18.
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {

    companion object {
        const val DIRECTION_SCROLL_DOWN = 1
        const val DIRECTION_SCROLL_UP = -1
    }

    override fun closeSoftKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}