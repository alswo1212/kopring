package com.kopring.repository

import com.kopring.domain.entity.User
import com.kopring.enums.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.jvm.optionals.getOrNull

@SpringBootTest
class UserRepositoryTest (
    @Autowired val userRepository: UserRepository
){
    @DisplayName("save test")
    @Test
    fun saveTest(){
        val user = User("testuserName", "12345678", Role.USER)
        val count = userRepository.count()
        val save = userRepository.save(user)

        assertThat(count).isEqualTo(userRepository.count() - 1)
        println(save)
    }

    @DisplayName("find test")
    @Test
    fun findByIdTest(){
        val userName = "tester"
        val userName2 = "1234"
        val test1 = userRepository.findById(userName2).getOrNull()
        assertThat(test1).isNull()

        val test2 = userRepository.findById(userName).getOrNull()
        assertThat(test2).isNotNull
    }

}