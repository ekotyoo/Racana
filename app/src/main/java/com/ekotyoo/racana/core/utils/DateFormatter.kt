package com.ekotyoo.racana.core.utils

import java.time.OffsetDateTime
import java.time.ZonedDateTime

fun formatDate(s: String): Long {
    val dateTime: ZonedDateTime = OffsetDateTime.parse(s).toZonedDateTime()
    return dateTime.toEpochSecond()
}