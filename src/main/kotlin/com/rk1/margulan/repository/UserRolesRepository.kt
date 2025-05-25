package com.rk1.margulan.repository

import com.rk1.margulan.model.UserRoles
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface UserRolesRepository: ReactiveCrudRepository<UserRoles, Long> {

    fun findAllByUserId(userId: Long): Flux<UserRoles>

}