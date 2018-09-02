package org.pic.argent.domain

import org.joda.time.LocalDateTime

data class Expense(
    val description: String?,
    val amount: Long,
    override val id: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : ModelWithTime

data class Asset(val amount: Long)
