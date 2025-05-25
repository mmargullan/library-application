package com.rk1.margulan.service

import com.rk1.margulan.config.JwtTokenUtil
import com.rk1.margulan.model.Role
import com.rk1.margulan.model.User
import com.rk1.margulan.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userRolesService: UserRolesService
) {

    fun addUser(user: User): Mono<User> {
        return userRepository.findByUsername(user.username!!)
            .flatMap <User> {
            Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists")) }
            .switchIfEmpty(
                userRepository.save(user).flatMap { savedUser ->
                    userRolesService.addRole(savedUser.id!!, "USER")
                        .thenReturn(savedUser)
                }
            )
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
                    userRolesService.findRoles(existingUser.id!!.toLong())
                        .map { it.name!! }
                        .collectList()
                        .flatMap { roleNames: List<String> ->
                            val token = jwtTokenUtil.generateToken(existingUser, roleNames)
                            Mono.just(token)
                        }
                }
            }
    }

}