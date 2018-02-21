package com.zcorp.opensportmanagement.utils.datetime

import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by romainz on 07/02/18.
 */
class DateTimeFormatter {

    companion object {
        val dateFormatterWithDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")
        val dateFormatterForMessages: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")
    }
}