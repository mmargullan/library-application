package com.rk1.margulan.repository

import com.rk1.margulan.model.Role
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface RoleRepository: ReactiveCrudRepository<Role, Long> {

    fun findByName(name: String): Mono<Role>
    fun findOneById(id: Long): Mono<Role>

}