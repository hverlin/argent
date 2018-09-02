package org.pic.argent.web

import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import org.pic.argent.config.AuthConfig
import org.pic.argent.config.JwtConfig
import org.pic.argent.config.JwtConfig.JWT_AUTH
import org.pic.argent.services.RestListRoute
import org.pic.argent.services.users.UserService

data class NewUser(val name: String, val password: String)


@Location("me")
class Me

@Location("myGroups")
class MyGroups

fun Route.userController(userService: UserService) {
    /**
     * A public login [Route] used to obtain JWTs
     */
    post("login") {
        val credentials = call.receive<UserPasswordCredential>()
        val user = userService.findUserByCredentials(credentials)
        val token = JwtConfig.makeToken(user)
        call.respondText(token)
    }

    authenticate(JWT_AUTH) {
        get<Me> {
            val user = call.principal<UserIdPrincipal>()
            call.respondText(user?.name!!)
        }

//        get<MyGroups> {
//            val user = call.principal<UserIdPrincipal>()!!
//            call.respond(userService.getUserAndGroup(user.name))
//        }
    }

    authenticate(AuthConfig.ADMIN_BASIC_AUTH) {
        route("/users") {

            get<RestListRoute> {
                call.respond(userService.listUsers(it.limit, it.offset))
            }

            post {
                call.respond(HttpStatusCode.Created, userService.addUser(call.receive()))
            }

        }
    }

}


