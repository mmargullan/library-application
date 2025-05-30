package com.rk1.margulan.repository

import com.rk1.margulan.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveCrudRepository<User, String> {

    fun findByUsername(username: String): Mono<User>

}