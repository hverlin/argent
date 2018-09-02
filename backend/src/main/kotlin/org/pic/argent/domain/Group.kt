package org.pic.argent.domain

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.joda.time.LocalDateTime

data class Group(
    val name: String,
    val currency: String,
    override val id: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : ModelWithTime

data class GroupWithMembersAndExpenses(
    val name: String,
    val currency: String,
    val members: List<Member>,
    val expenses: List<Expense>,
    override val id: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : ModelWithTime


data class GroupWithDetails(
    val currency: CurrencyUnit,
    val members: List<UserWithAssets>
)

fun GroupWithDetails.makeMoneyGraph() =
    this.members
        .sortedBy { it.username }
        .map {
            it.assets.fold(Money.ofMinor(this.currency, 0)) { total, asset ->
                total + Money.ofMinor(this.currency, asset.amount)
            }
        }.toTypedArray()
