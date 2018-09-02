package org.pic.argent.domain

import org.joda.time.LocalDateTime

data class Member(
    val name: String,
    override val id: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : ModelWithTime

