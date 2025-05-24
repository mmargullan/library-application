package com.rk1.margulan.config

import com.rk1.margulan.repository.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtUserDetailsService(
    private val userRepository: UserRepository
): ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return userRepository.findByUsername(username)
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found with username: $username")))
            .map { user -> SpringSecurityUser(user) }
    }

}