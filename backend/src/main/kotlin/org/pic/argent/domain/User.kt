package org.pic.argent.domain

import org.joda.time.LocalDateTime

data class User(
    val username: String,
    val firstName: String?,
    val lastName: String?,
    override val id: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : ModelWithTime


data class UserWithAssets(
    val username: String,
    val assets: List<Asset>
)
