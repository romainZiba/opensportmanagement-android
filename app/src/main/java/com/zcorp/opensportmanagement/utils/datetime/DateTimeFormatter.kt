package com.zcorp.opensportmanagement.utils.datetime

import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * Created by romainz on 07/02/18.
 */
class DateTimeFormatter {

    companion object {
        val dateFormatterWithDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")
        val dateFormatterForMessages: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")
    }
}

fun dateFormatter(): DateTimeFormatter {
    return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
}