package com.rk1.margulan.service

import com.rk1.margulan.config.JwtTokenUtil
import com.rk1.margulan.model.User
import com.rk1.margulan.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil
) {

    fun addUser(user: User): Mono<User> {
        user.roleId = 1
        return userRepository.findByUsername(user.username!!)
            .flatMap <User> {
            Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists")) }
            .switchIfEmpty(userRepository.save(user))
    }

    fun login(user: User): Mono<String> {
        return userRepository.findByUsername(user.username!!)
            .switchIfEmpty(
                Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist"))
            )
            .flatMap { existingUser ->
                if (existingUser.password != user.password) {
                    Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"))
                } else {
                    val token = jwtTokenUtil.generateToken(existingUser, "USER")
                    Mono.just(token)
                }
            }
    }

}