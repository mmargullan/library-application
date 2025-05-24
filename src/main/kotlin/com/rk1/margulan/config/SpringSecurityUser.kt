package com.rk1.margulan.config

import com.rk1.margulan.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SpringSecurityUser(
    private val user: User
) : UserDetails {

    override fun getUsername(): String = user.username ?: ""

    override fun getPassword(): String = user.password ?: ""

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return emptyList()
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}