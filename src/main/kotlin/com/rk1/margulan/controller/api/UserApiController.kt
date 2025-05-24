package com.rk1.margulan.controller.api

import com.rk1.margulan.model.User
import com.rk1.margulan.repository.UserRepository
import com.rk1.margulan.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/users")
class UserApiController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): Mono<User> {
        return userService.addUser(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User): Mono<String> {
        return userService.login(user)
    }

}