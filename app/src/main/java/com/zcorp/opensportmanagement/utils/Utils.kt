package com.zcorp.opensportmanagement.utils

import java.text.DateFormat
import java.util.*

/**
 * Created by romainz on 07/02/18.
 */
class Utils {

    companion object {
        fun format(date: Date, locale: Locale): String {
            return DateFormat.getDateInstance(DateFormat.FULL, locale).format(date)
        }
    }
}