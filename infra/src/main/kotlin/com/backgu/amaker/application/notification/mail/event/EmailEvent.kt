package com.backgu.amaker.application.notification.mail.event

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.infra.mail.constants.EmailConstants
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

open class EmailEvent
    @JsonCreator
    constructor(
        @JsonProperty("email") val email: String,
        @JsonProperty("emailConstants") val emailConstants: EmailConstants,
        @JsonProperty("title") override val title: String,
        @JsonProperty("content") override val content: String,
    ) : NotificationEvent
