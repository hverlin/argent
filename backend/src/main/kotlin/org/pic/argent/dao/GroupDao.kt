package org.pic.argent.dao

import org.jetbrains.exposed.dao.EntityID
import org.pic.argent.domain.Group
import org.pic.argent.domain.GroupWithMembersAndExpenses
import org.pic.argent.repository.*
import java.util.*


class GroupDao(id: EntityID<UUID>) : EntityWithTime(id, GroupTable) {
    companion object : EntityWithTimeClass<GroupDao>(GroupTable)

    var name by GroupTable.name
    var currency by GroupTable.currency

    val members by MemberDao referrersOn MemberTable.group
    val expenses by ExpenseDao referrersOn ExpenseTable.group

    fun toGroup(): Group = Group(
        this.name,
        this.id.toString(),
        this.currency,
        this.createdAt.toLocalDateTime(),
        this.updatedAt.toLocalDateTime()
    )

    fun toGroupWithMembersAndExpenses(): GroupWithMembersAndExpenses = GroupWithMembersAndExpenses(
        this.name,
        this.currency,
        this.members.toList().map { it.toMember() },
        this.expenses.toList().map { it.toExpense() },
        this.id.toString(),
        this.createdAt.toLocalDateTime(),
        this.updatedAt.toLocalDateTime()
    )
}
