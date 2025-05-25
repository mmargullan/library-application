package com.rk1.margulan.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user_roles")
class UserRoles {

    @Id
    var id: String? = null

    @Column("user_id")
    var userId: Long? = null

    @Column("role_id")
    var roleId: Long? = null

}