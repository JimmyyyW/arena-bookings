package com.cwagritech.arenabookings.persistence

import com.cwagritech.arenabookings.model.Role
import com.cwagritech.arenabookings.model.UserRole
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<UserRole, Int> {

    fun findByRoleName(roleName: Role): UserRole
}