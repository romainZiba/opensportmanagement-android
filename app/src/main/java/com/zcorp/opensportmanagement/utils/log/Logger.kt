package com.zcorp.opensportmanagement.utils.log

import android.util.Log

/**
 * Created by romainz on 23/03/18.
 */
class Logger : ILogger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun e(tag: String, message: String, e: Exception) {
        Log.e(tag, message, e)
    }
}