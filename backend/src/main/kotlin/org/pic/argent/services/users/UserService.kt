package org.pic.argent.services.users

import io.ktor.auth.UserPasswordCredential
import org.pic.argent.dao.UserDao
import org.pic.argent.domain.Group
import org.pic.argent.domain.User
import org.pic.argent.repository.DatabaseFactory.dbQuery
import org.pic.argent.repository.UserTable.password
import org.pic.argent.repository.UserTable.username
import org.pic.argent.services.RestListResponse
import org.pic.argent.web.NewUser
import java.util.*


class UserService {

    suspend fun listUsers(limit: Int, offset: Int = 0): RestListResponse<User> = dbQuery {
        RestListResponse(
            data = UserDao.all().limit(limit, offset).map { it.toUser() },
            count = UserDao.all().count()
        )
    }

    private suspend fun getUserById(id: UUID): User? = dbQuery {
        UserDao.findById(id)?.toUser()
    }

    suspend fun addUser(newUser: NewUser): User = dbQuery {
        UserDao.new {
            username = newUser.name
            password = newUser.password
        }
    }.toUser()

    suspend fun findUserByCredentials(credentials: UserPasswordCredential): User = dbQuery {
        UserDao.findOne {
            username eq credentials.name
            password eq credentials.password
        }
    }.toUser()

//    suspend fun getUserAndGroup(id: String): RestListResponse<Group> = dbQuery {
//        RestListResponse(UserDao[UUID.fromString(id)].groups.map { it.toGroup() })
//    }

}
