package org.pic.argent.repository

import org.jetbrains.exposed.sql.Table

object GroupTable : TableWithTime() {
    val name = varchar("name", 255).uniqueIndex()
    val currency = varchar("currency", 255)
}

object UserTable : TableWithTime() {
    val username = varchar("username", 255).uniqueIndex()
    val firstName = varchar("first_name", 255).nullable()
    val lastName = varchar("last_name", 255).nullable()
    val password = varchar("password", 255).nullable()
}

object MemberTable : TableWithTime() {
    val name = varchar("name", 255).index()
    val group = reference("group", GroupTable).index()
}

object MemberUserTable : Table() {
    val member = reference("member", MemberTable).primaryKey(0)
    val user = reference("user", UserTable).primaryKey(1)
}

object ExpenseTable : TableWithTime() {
    val description = text("description").nullable()
    val amount = long("amount")
    val createdBy = reference("created_by", MemberTable)
    val group = reference("group", GroupTable)
}

object ExpenseMemberTable : Table() {
    val expense = reference("expense", ExpenseTable).primaryKey(0)
    val member = reference("member", MemberTable).primaryKey(1)
    val amountPaid = long("amount_paid")
    val amountDue = long("amount_due")
}
