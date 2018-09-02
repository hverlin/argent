package org.pic.argent

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.pipeline.PipelineContext
import io.ktor.response.respond
import org.jetbrains.exposed.exceptions.EntityNotFoundException
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.pic.argent.utils.*

fun StatusPages.Configuration.errorHandling() {
    exception<Throwable> {
        // TODO: @hverlin return runtime error in prod
        respondWithRestError(it, HttpStatusCode.InternalServerError)
    }
    exception< ExposedSQLException> {
        // TODO: @hverlin return invalid request error in prod
        respondWithRestError(it, HttpStatusCode.BadRequest)
    }
    exception<InvalidRequestException> {
        respondWithRestError(it, HttpStatusCode.BadRequest)
    }
    exception<EntityNotFoundException> {
        respondWithRestError(it, HttpStatusCode.NotFound)
    }
    exception<NotFoundException> {
        respondWithRestError(it, HttpStatusCode.NotFound)
    }
    exception<AuthenticationException> {
        respondWithRestError(it, HttpStatusCode.Unauthorized)
    }
    exception<AuthorizationException> {
        respondWithRestError(it, HttpStatusCode.Forbidden)
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.respondWithRestError(e: Throwable, status: HttpStatusCode) =
    call.respond(status, e.toErrorMessage(status = status))
