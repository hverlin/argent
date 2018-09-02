package org.pic.argent.config

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import org.pic.argent.domain.User
import java.util.*

object JwtConfig {
    const val JWT_AUTH: String = "jwt"

    private const val secret = "zAP5MBA4B4Ijz0MZaS48"
    private const val issuer = "ARGENT.APP"
    private const val validityInMs = 36_000_00 * 24 // 24 hours
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun makeToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id)
        .withExpiresAt(getExpiration())
        .sign(algorithm)


    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}
