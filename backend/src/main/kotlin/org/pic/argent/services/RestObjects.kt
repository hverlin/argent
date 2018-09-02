package org.pic.argent.services

import io.ktor.locations.Location
import org.pic.argent.utils.InvalidRequestException
import java.util.*

data class RestListResponse<T>(
    val data: List<T>,
    val count: Int = data.size
)

@Location("")
data class RestListRoute(val limit: Int = 50, val offset: Int = 0)

@Location("id/{id}")
data class RestReadById(val id: String)

fun RestReadById.validateUUID() {
    try {
        UUID.fromString(this.id)
    } catch (e: Exception) {
        throw InvalidRequestException("Invalid UUID")
    }
}

fun String?.toUUID(): UUID {
    try {
        return UUID.fromString(this)
    } catch (e: Exception) {
        throw InvalidRequestException("Invalid ID")
    }
}

