package org.pic.argent.dao

import org.jetbrains.exposed.dao.EntityID
import org.pic.argent.domain.Expense
import org.pic.argent.repository.EntityWithTime
import org.pic.argent.repository.EntityWithTimeClass
import org.pic.argent.repository.ExpenseTable
import java.util.*


class ExpenseDao(id: EntityID<UUID>) : EntityWithTime(id, ExpenseTable) {
    companion object : EntityWithTimeClass<ExpenseDao>(ExpenseTable)

    var description by ExpenseTable.description
    var amount by ExpenseTable.amount
    var group by GroupDao referencedOn ExpenseTable.group
    var createdBy by MemberDao referencedOn ExpenseTable.createdBy

    fun toExpense() = Expense(
        this.description,
        this.amount,
        this.id.toString(),
        this.createdAt.toLocalDateTime(),
        this.createdAt.toLocalDateTime()
    )
}


