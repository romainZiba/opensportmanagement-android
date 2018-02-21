package com.zcorp.opensportmanagement.model

import org.threeten.bp.OffsetDateTime

/**
 * Created by romainz on 16/02/18.
 */
data class InAppMessage(val message: String, val from: String, val time: OffsetDateTime) {
    var id: String? = null
}