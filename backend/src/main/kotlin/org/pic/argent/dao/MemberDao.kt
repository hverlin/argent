package org.pic.argent.dao

import org.jetbrains.exposed.dao.EntityID
import org.pic.argent.domain.Member
import org.pic.argent.repository.EntityWithTime
import org.pic.argent.repository.EntityWithTimeClass
import org.pic.argent.repository.MemberTable
import java.util.*

class MemberDao(id: EntityID<UUID>) : EntityWithTime(id, MemberTable) {
    companion object : EntityWithTimeClass<MemberDao>(MemberTable)

    var name by MemberTable.name
    var group by GroupDao referencedOn MemberTable.group

    fun toMember(): Member = Member(
        this.name,
        this.id.toString(),
        this.createdAt.toLocalDateTime(),
        this.updatedAt.toLocalDateTime()
    )
}

