package org.pic.argent.services.groups


import org.pic.argent.dao.ExpenseDao
import org.pic.argent.dao.GroupDao
import org.pic.argent.dao.MemberDao
import org.pic.argent.domain.Group
import org.pic.argent.domain.GroupWithMembersAndExpenses
import org.pic.argent.domain.Member
import org.pic.argent.repository.DatabaseFactory.dbQuery
import org.pic.argent.repository.GroupTable
import org.pic.argent.repository.MemberTable
import org.pic.argent.services.RestListResponse
import java.util.*

data class NewGroup(val name: String, val currency: String)

data class NewMember(val name: String)

data class NewExpense(val groupName: String, val amount: Long, val createdBy: String, val description: String?)


class GroupService {

    suspend fun listGroups(limit: Int, offset: Int = 0): RestListResponse<Group> = dbQuery {
        RestListResponse(
            data = GroupDao.all().limit(limit, offset).map { it.toGroup() },
            count = GroupDao.all().count()
        )
    }

    suspend fun listGroupsWithMembers(limit: Int, offset: Int = 0) = dbQuery {
        RestListResponse(
            data = GroupDao.all().limit(limit, offset).map { it.toGroupWithMembersAndExpenses() },
            count = GroupDao.all().count()
        )
    }

    suspend fun getGroupById(id: UUID): GroupWithMembersAndExpenses? = dbQuery {
        GroupDao.findById(id)?.toGroupWithMembersAndExpenses()
    }

    suspend fun getGroupByName(name: String): GroupWithMembersAndExpenses = dbQuery {
        GroupDao.findOne { GroupTable.name eq name }.toGroupWithMembersAndExpenses()
    }

    suspend fun addGroup(newGroup: NewGroup): Group = dbQuery {
        GroupDao.new {
            name = newGroup.name
            currency = newGroup.currency
        }
    }.toGroup()

    suspend fun addMemberToGroup(newMember: NewMember, groupId: UUID): Member = dbQuery {
        MemberDao.new {
            name = newMember.name
            group = GroupDao.findById(groupId)!!
        }
    }.toMember()

    suspend fun deleteGroup(name: String) = dbQuery {
        GroupDao.findOne { GroupTable.name eq name }.delete()
    }

    suspend fun addExpenseToGroup(expense: NewExpense, groupId: UUID) = dbQuery {
        val member = MemberDao.findOne { MemberTable.name eq expense.createdBy }
        ExpenseDao.new {
            amount = expense.amount
            group = GroupDao.findById(groupId)!!
            createdBy = member
            description = expense.description
        }
    }.toExpense()

}
