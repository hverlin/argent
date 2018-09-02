package org.pic.argent.web

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.StatusPages
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import org.joda.time.LocalDateTime
import org.pic.argent.errorHandling
import org.pic.argent.utils.NotFoundException

fun Route.mainController() {
    route("*") {
        handle { throw NotFoundException("this route does not exist") }
    }

    static("/static") {
        resources("static")
    }

    get("/") {
        call.respondText("ARGENT 0.0.1")
    }

    get("/health") {
        call.respond(mapOf("status" to "up", "time" to LocalDateTime.now()))
    }

    install(StatusPages) {
        errorHandling()
    }
}
