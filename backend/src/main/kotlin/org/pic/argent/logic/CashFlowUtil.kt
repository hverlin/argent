package org.pic.argent.logic

import org.joda.money.CurrencyUnit
import org.joda.money.Money

data class Transaction(val from: Int, val to: Int, val amount: Money)

object CashFlowUtil {
    private fun getMinIndex(arr: Array<Money>) = arr.foldIndexed(0) { idx, minIndex, next ->
        if (next < arr[minIndex]) idx else minIndex
    }

    private fun getMaxIndex(arr: Array<Money>) = arr.foldIndexed(0) { idx, maxIndex, next ->
        if (next > arr[maxIndex]) idx else maxIndex
    }

    fun computeMinCashFlow(group: Array<Money>, currencyUnit: CurrencyUnit): List<Transaction> {
        val transactions: MutableList<Transaction> = ArrayList()

        // amounts[p] indicates the net amounts to be credited/debited to/from person 'p'
        fun minCashFlowRec(amounts: Array<Money>): List<Transaction> {
            val maxCreditIdx = getMaxIndex(amounts)
            val maxDebitIdx = getMinIndex(amounts)

            // If both amounts are 0, then all amounts are settled
            if (amounts[maxCreditIdx].isEqual(Money.ofMinor(currencyUnit, 0)) &&
                amounts[maxDebitIdx].isEqual(Money.ofMinor(currencyUnit, 0))
            )
                return transactions.toList()

            // otherwise there is a debt
            val amountDue = if (amounts[maxCreditIdx].isLessThan(amounts[maxDebitIdx].negated()))
                amounts[maxCreditIdx]
            else
                amounts[maxDebitIdx].negated()

            amounts[maxCreditIdx] -= amountDue
            amounts[maxDebitIdx] += amountDue

            transactions.add(Transaction(from = maxDebitIdx, to = maxCreditIdx, amount = amountDue))

            return minCashFlowRec(amounts) // Continue until everybody is settled
        }

        return minCashFlowRec(group)
    }
}
