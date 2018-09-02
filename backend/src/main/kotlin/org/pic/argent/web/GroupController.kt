package org.pic.argent.web

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import org.pic.argent.config.AuthConfig
import org.pic.argent.services.RestListRoute
import org.pic.argent.services.RestReadById
import org.pic.argent.services.groups.GroupService
import org.pic.argent.services.toUUID
import org.pic.argent.utils.NotFoundException


fun Route.groupController(groupService: GroupService) {
    route("/groups") {

        authenticate(AuthConfig.ADMIN_BASIC_AUTH) {
            get<RestListRoute> {
                call.respond(groupService.listGroupsWithMembers(it.limit, it.offset))
            }
        }

        get<RestReadById> {
            val group = groupService.getGroupById(it.id.toUUID())
                ?: throw NotFoundException("group with id ${it.id} not found")
            call.respond(group)
        }

        get<ReadByName> {
            call.respond(groupService.getGroupByName(it.name))
        }

        post("") {
            call.respond(HttpStatusCode.Created, groupService.addGroup(call.receive()))
        }

        post("{id}/createExpense") {
            val groupId = call.parameters["id"].toUUID()
            call.respond(HttpStatusCode.Created, groupService.addExpenseToGroup(call.receive(), groupId))
        }

        post("{id}/addMember") {
            val groupId = call.parameters["id"].toUUID()
            call.respond(HttpStatusCode.Created, groupService.addMemberToGroup(call.receive(), groupId))
        }
    }
}

@Location("{name}")
data class ReadByName(val name: String)
