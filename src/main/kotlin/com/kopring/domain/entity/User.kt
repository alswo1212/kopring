package com.kopring.domain.entity

import com.kopring.enums.Role
import jakarta.persistence.*

@Entity(name = "USERS")
@Table
class User(
    @Id
    val userName:String,
    @Column(nullable = false)
    private val password:String,
    @Enumerated(EnumType.STRING)
    val role: Role
) {
    fun checkPw(pw:String):Boolean{
        return pw == password
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return userName == other.userName
    }

    override fun hashCode(): Int = userName.hashCode()
    override fun toString(): String {
        return "User(userName='$userName', role=$role)"
    }


}