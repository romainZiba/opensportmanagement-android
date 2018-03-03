package com.zcorp.opensportmanagement.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.threeten.bp.OffsetDateTime

/**
 * Created by romainz on 16/02/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class InAppMessage(val conversationId: String, val conversationTopic: String, val from: String, val message: String, val time: OffsetDateTime)