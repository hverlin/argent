package org.pic.argent.logic

import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.specs.StringSpec
import io.kotlintest.specs.Test
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.pic.argent.domain.Asset
import org.pic.argent.domain.GroupWithDetails
import org.pic.argent.domain.UserWithAssets
import org.pic.argent.domain.makeMoneyGraph

@Test
class CashFlowTest : StringSpec({
    "should compute cash the cash flow correctly" {
        val hugues = UserWithAssets(
            "hugues", listOf(
                Asset(100),
                Asset(10),
                Asset(-20)
            )
        )

        val jack = UserWithAssets(
            "jack", listOf(
                Asset(-20),
                Asset(20),
                Asset(-20)
            )
        )

        val anna = UserWithAssets(
            "anna", listOf(
                Asset(-40),
                Asset(5),
                Asset(60)
            )
        )

        val emilia = UserWithAssets(
            "emilia", listOf(
                Asset(-40),
                Asset(-35),
                Asset(-20)
            )
        )

        val users = listOf(anna, emilia, hugues, jack)
        val group = GroupWithDetails(members = users, currency = CurrencyUnit.EUR)

        val minCashFlow = CashFlowUtil.computeMinCashFlow(group.makeMoneyGraph(), group.currency)

        minCashFlow.shouldContainExactly(
            listOf(
                Transaction(from = 1, to = 2, amount = Money.parse("EUR 0.90")),
                Transaction(from = 3, to = 0, amount = Money.parse("EUR 0.20")),
                Transaction(from = 1, to = 0, amount = Money.parse("EUR 0.05"))
            )
        )
    }
})
