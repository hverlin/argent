package org.pic.argent.domain

import org.joda.time.LocalDateTime

interface ModelWithTime {
    val id: String
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}
