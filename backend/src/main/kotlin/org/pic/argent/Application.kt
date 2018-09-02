package org.pic.argent

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.auth.jwt.jwt
import io.ktor.content.CachingOptions
import io.ktor.features.*
import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.routing.Routing
import io.ktor.sessions.Sessions
import org.pic.argent.config.AuthConfig.ADMIN_BASIC_AUTH
import org.pic.argent.config.JwtConfig
import org.pic.argent.services.groups.GroupService
import org.pic.argent.services.users.UserService
import org.pic.argent.repository.DatabaseFactory
import org.pic.argent.web.groupController
import org.pic.argent.web.mainController
import org.pic.argent.web.userController
import org.slf4j.event.Level
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

fun Application.module() {
    val groupService = GroupService()
    val userService = UserService()

    DatabaseFactory.initializeDatabase()

    install(Locations)
    install(Sessions)
    install(AutoHeadResponse)
    install(ConditionalHeaders)
    install(DataConversion)
    install(PartialContent) { maxRangeCount = 10 }


    install(Compression) {
        gzip { priority = 1.0 }
        deflate {
            priority = 10.0
            minimumSize(1024)
        }
    }


    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // not in prod
    }

    install(CachingHeaders) {
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                else -> null
            }
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }

    val isBehindProxy = environment.config.propertyOrNull("argentApp.security.behindProxy")?.getString() ?: "false"
    if (isBehindProxy.toBoolean()) {
        install(ForwardedHeaderSupport)
        install(XForwardedHeadersSupport)
    }


    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(Authentication) {
        basic(ADMIN_BASIC_AUTH) {
            realm = environment.config.property("jwt.realm").getString()
            val adminUser = environment.config.property("argentApp.security.adminUser").getString()
            val adminPassword = environment.config.property("argentApp.security.adminPassword").getString()
            validate {
                if (it.name == adminUser && it.password == adminPassword) UserIdPrincipal(it.name)
                else null
            }
        }

        jwt("jwt") {
            verifier(JwtConfig.verifier)
            realm = environment.config.property("jwt.realm").getString()
            validate {
                UserIdPrincipal(it.payload.getClaim("id").asString())
            }
        }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())
            registerModule(JodaModule())
        }
    }

    install(Routing) {
        mainController()
        userController(userService)
        groupController(groupService)
    }
}


