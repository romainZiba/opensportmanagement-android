package com.zcorp.opensportmanagement.utils.log

/**
 * Created by romainz on 23/03/18.
 */
interface ILogger {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun e(tag: String, message: String, e: Exception)
}