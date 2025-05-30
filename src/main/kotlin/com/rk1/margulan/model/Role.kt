package com.rk1.margulan.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("roles")
class Role {

    @Id
    var id: Long? = null

    @Column
    var name: String? = null

}