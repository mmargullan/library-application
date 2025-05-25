package com.rk1.margulan.service

import com.rk1.margulan.model.Role
import com.rk1.margulan.model.UserRoles
import com.rk1.margulan.repository.RoleRepository
import com.rk1.margulan.repository.UserRolesRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserRolesService(
    private val userRolesRepository: UserRolesRepository,
    private val roleRepository: RoleRepository
) {

    fun addRole(userId: Long, roleName: String): Mono<UserRoles> {
        return roleRepository.findByName(roleName)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "No role")))
            .flatMap { role ->
                val userRole = UserRoles()
                userRole.userId = userId
                userRole.roleId = role.id
                userRolesRepository.save(userRole)
            }
    }

    fun findRoles(userId: Long): Flux<Role> {
        return userRolesRepository.findAllByUserId(userId)
            .flatMap { userRole ->
                roleRepository.findOneById(userRole.roleId!!)
            }
    }

}