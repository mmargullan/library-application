package com.rk1.margulan.config

import com.rk1.margulan.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil(
    @Value("\${jwt.secret}}") private val secret: String,
    @Value("\${jwt.validation}") private val validationTime: Int,
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(user: User, role: String): String {
        return Jwts.builder()
            .setSubject(user.username)
            .claim("role", role)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + validationTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJwt(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJwt(token)
            .body
    }

    fun getUsernameFromToken(token: String): String? {
        return getAllClaimsFromToken(token).subject
    }

    fun getRoleFromToken(token: String): String? {
        return getAllClaimsFromToken(token)["role"].toString()
    }

}