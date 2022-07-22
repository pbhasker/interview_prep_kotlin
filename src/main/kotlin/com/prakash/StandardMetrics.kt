package com.prakash

import java.time.Instant

/**
 * FinanceRound
 * Investment
 *
 *
 *
 * Company
 * Investor
 */

data class Investor(val id: String, val name: String, val shareHoldings: List<ShareHolding>) {
    fun computeFairValueForSingleCompany(company: Company, timestamp: Instant): Double {
        val relevantHoldings = shareHoldings.filter { holding -> holding.company == company }.filter { holding -> holding.financingRound.timestamp.isBefore(timestamp) }

        val price = StandardMetrics.getMostRecentPrice(company, timestamp)

        val shareQuantity = relevantHoldings.filter { holding -> holding.type == HoldingType.STANDARD }.map { holding -> holding.getCurrentQuantity(timestamp) }.sum()

        val debtValue = relevantHoldings.filter { holding -> holding.type == HoldingType.DEBT }.map { holding -> holding.getCurrentQuantity(timestamp) * holding.financingRound.price}.sum()

        return debtValue + (price * shareQuantity)
    }

    fun computeFairValueForAll(timestamp: Instant): Double {
        val companies = shareHoldings.map { holding -> holding.company }.toSet()
        return companies.map { company ->  computeFairValueForSingleCompany(company, timestamp) }.sum()
    }

    fun realizedGains(): Double = 0.0
}

enum class HoldingType {
    STANDARD,
    DEBT,
}

data class ShareHolding(val company: Company, val purchaseQuantity: Int, val financingRound: FinanceRound, val sellEvents: MutableList<SellEvent>, val type: HoldingType) {
    fun sellShares(quantity: Int, price: Double) {
        sellEvents.add(SellEvent(company, quantity, price, Instant.now()))
    }

    fun getCurrentQuantity(timestamp: Instant): Int {
        val relevantSellEvents = sellEvents.filter { sellEvent -> sellEvent.timestamp.isBefore(timestamp) }
        return purchaseQuantity - relevantSellEvents.map { event -> event.quantity }.sum()
    }
}

data class Company(val id: String, val name: String)

data class FinanceRound(val company: Company, val quantity: Int, val price: Double, val timestamp: Instant)

data class SellEvent(val company: Company, val quantity: Int, val price: Double, val timestamp: Instant)

object StandardMetrics {

    fun getMostRecentPrice(company: Company, timestamp: Instant): Double {
        // TODO - call Dynamo
        return 0.0
    }

    fun main() {
        val company = Company("firstCompany", "firstCompany")

        val financeRound1 = FinanceRound(company, 100, 10.0, Instant.now())
        val financeRound2 = FinanceRound(company, 100, 20.0, Instant.now())

        val investor1 = Investor("investor1", "investor1", listOf(
            ShareHolding(company, 50, financeRound1, mutableListOf<SellEvent>(), HoldingType.STANDARD))
        )
        val investor2 = Investor("investor2", "investor2", listOf(
            ShareHolding(company, 50, financeRound2, mutableListOf<SellEvent>(), HoldingType.STANDARD))
        )

        val investors = listOf(investor1, investor2)

        investors.map { investor -> investor.computeFairValueForSingleCompany(company, Instant.now()) }.sum()

    }
}