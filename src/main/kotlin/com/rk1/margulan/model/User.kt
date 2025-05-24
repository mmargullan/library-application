package com.rk1.margulan.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
class User {

    @Id
    var id: String? = null

    @Column
    var username: String? = null

    @Column
    var password: String? = null

    @Column("role_id")
    var roleId: Long? = null

}