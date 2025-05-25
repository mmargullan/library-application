package com.rk1.margulan.service

import com.rk1.margulan.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
}