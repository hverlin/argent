package org.pic.argent.repository

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

fun nowUtc(): DateTime = DateTime.now(DateTimeZone.UTC)

open class TableWithTime : UUIDTable() {
    val createdAt = this.datetime("created_at").default(nowUtc())
    val updatedAt = this.datetime("updated_at").default(nowUtc())
}

abstract class EntityWithTime(id: EntityID<UUID>, table: TableWithTime) : UUIDEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
}

abstract class EntityWithTimeClass<E : EntityWithTime>(table: TableWithTime) : UUIDEntityClass<E>(table) {
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = nowUtc()
                } catch (_: Exception) {
                }
            }
        }
    }

    fun findOne(op: SqlExpressionBuilder.() -> Op<Boolean>): E =
        this.find(SqlExpressionBuilder.op()).limit(1).firstOrNull()
            ?: throw IllegalArgumentException("unable to find entity")

}
