package com.rk1.margulan.controller.ui

import com.rk1.margulan.repository.UserRepository
import com.rk1.margulan.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userRepository: UserRepository
) {

    @GetMapping("/register")
    fun create(model: Model): String {
        return "register_page"
    }

}