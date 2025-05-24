package com.rk1.margulan.repository

import com.rk1.margulan.model.Role
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: ReactiveCrudRepository<Role, Long> {
}