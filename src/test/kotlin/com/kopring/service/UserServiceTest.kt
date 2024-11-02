package com.kopring.service

import com.kopring.domain.dto.UserDTO
import com.kopring.repository.UserRepository
import com.kopring.util.JwtUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletResponse

@SpringBootTest
class UserServiceTest (
    @Autowired val userService: UserService,
    @Autowired val userRepository: UserRepository,
    @Autowired val jwtUtil: JwtUtil
){
    @DisplayName("signup test")
    @Test
    fun signupTest(){
        val dto = UserDTO("test2User!", "1234asdf")
        val count = userRepository.count()
        userService.signup(dto)
        assertThat(count).isEqualTo(userRepository.count() - 1)
    }
    @DisplayName("signup dupl test")
    @Test
    fun signupDuplTest(){
        val dto = UserDTO("userName", "1234asdf")
        try {
            userService.signup(dto)
        }catch (e:IllegalArgumentException){
            println("유저명 검사 ok")
        }

    }

    @DisplayName("login test")
    @Test
    fun loginTest(){
        val test = UserDTO("userName", "123456789")
        try {
            userService.login(test, MockHttpServletResponse())
        }catch (e:IllegalArgumentException){
            println("패스워드 검사 ok")
        }
        val dto = UserDTO("userName", "12345678")
        val response = MockHttpServletResponse()
        userService.login(dto, response)
        val token = response.getHeader(jwtUtil.header)
        assertThat(token).isNotNull()
        println(token)

    }

}