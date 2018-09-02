package org.pic.argent.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.money.CurrencyUnit
import org.pic.argent.dao.ExpenseDao
import org.pic.argent.dao.GroupDao
import org.pic.argent.dao.MemberDao
import org.pic.argent.dao.UserDao
import org.pic.argent.repository.GroupTable.currency
import org.pic.argent.repository.GroupTable.name

import java.util.*
import java.util.logging.Logger
import kotlin.coroutines.experimental.CoroutineContext

object DatabaseFactory {

    private val logger = Logger.getLogger("Database logger")

    private val database by lazy {
        Database.connect(hikari())
    }

    private fun createTables() {
        create(GroupTable)
        create(UserTable)
        create(MemberTable)
        create(ExpenseTable)
        create(MemberUserTable)
    }

    private fun insertInitialRecords() {
        val firstGroup = GroupDao.new {
            name = "first group"
            currency = CurrencyUnit.EUR.toString()
        }

        val secondGroup = GroupDao.new {
            name = "second group"
            currency = CurrencyUnit.EUR.toString()
        }

        GroupDao.new {
            name = "third group"
            currency = CurrencyUnit.EUR.toString()
        }

        val firstMember = MemberDao.new {
            name = "member 1"
            group = firstGroup
        }

        val secondMember = MemberDao.new {
            name = "member 2"
            group = firstGroup
        }

        val userOne = UserDao.new(UUID.randomUUID()) {
            username = "user1"
        }

        UserDao.new(UUID.randomUUID()) {
            username = "user2"
        }

        ExpenseDao.new {
            amount = 10000
            group = firstGroup
            createdBy = firstMember
            description = "test expense"
        }

    }

    fun initializeDatabase() {
        val db = database
        logger.info(db.url)

        transaction {
            try {
                createTables()
                insertInitialRecords()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(20, "database-pool")
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(dispatcher) {
        transaction { block() }
    }
}
