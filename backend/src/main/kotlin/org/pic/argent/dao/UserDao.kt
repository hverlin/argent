package org.pic.argent.dao

import org.jetbrains.exposed.dao.EntityID
import org.pic.argent.domain.User
import org.pic.argent.repository.*
import java.util.*

class UserDao(id: EntityID<UUID>) : EntityWithTime(id, UserTable) {
    companion object : EntityWithTimeClass<UserDao>(UserTable)

    var username by UserTable.username
    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var password by UserTable.password

    fun toUser(): User = User(
        this.username,
        this.firstName,
        this.lastName,
        this.id.toString(),
        this.createdAt.toLocalDateTime(),
        this.updatedAt.toLocalDateTime()
    )
}

