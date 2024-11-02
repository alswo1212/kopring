package com.kopring.repository

import com.kopring.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User, String> {
}