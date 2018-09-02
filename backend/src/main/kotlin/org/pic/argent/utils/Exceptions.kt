package org.pic.argent.utils

import com.google.common.base.Throwables
import io.ktor.http.HttpStatusCode
import java.time.LocalDateTime


class AuthenticationException : RuntimeException()
class NotFoundException(override val message: String?) : RuntimeException()
class AuthorizationException : RuntimeException()
class InvalidRequestException(override val message: String) : RuntimeException()

data class ErrorMessage(
    val message: String? = "",
    val status: Int = HttpStatusCode.InternalServerError.value,
    val stackTrace: String = "",
    val timestamp: String = LocalDateTime.now().toString()
)

fun Throwable.toErrorMessage(status: HttpStatusCode = HttpStatusCode.InternalServerError) =
    ErrorMessage(
        message = this.message,
        stackTrace = getStacktraceAsString(this),
        status = status.value
    )

fun getStacktraceAsString(e: Throwable): String =
    if (System.getenv("disableStackTrace") === "true") "" else Throwables.getStackTraceAsString(e)!!
