package com.rk1.margulan.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userDetailsService: JwtUserDetailsService,
) : WebFilter {

    private val logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    private val permittedPath = listOf(
        "/api/users/register",
        "/api/users/login",
        "users/register",
        "users/login"
    )

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.path.pathWithinApplication().toString()
        if (permittedPath.contains(path)) {
            return chain.filter(exchange)
        }
        logger.info("Filtration in process")
        val header = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (header == null || !header.startsWith("Bearer ")) {
            unauthorized(exchange)
        }
        val token = header?.removePrefix("Bearer ") ?: return unauthorized(exchange)
        if (jwtTokenUtil.validateToken(token)) {
            unauthorized(exchange)
        }
        val username = jwtTokenUtil.getUsernameFromToken(token)
            ?: unauthorized(exchange)

        return userDetailsService.findByUsername(username.toString())
            .flatMap { userDetails ->
                val auth = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                val securityContext = SecurityContextImpl(auth)
                exchange.attributes["SPRING_SECURITY_CONTEXT"] = securityContext
                chain.filter(exchange)
            }
            .switchIfEmpty(unauthorized(exchange))
    }

    private fun unauthorized(exchange: ServerWebExchange): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }

}