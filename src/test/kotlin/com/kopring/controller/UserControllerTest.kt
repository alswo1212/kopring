package com.kopring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kopring.domain.dto.UserDTO
import com.kopring.util.JwtUtil
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Rollback(false)
class UserControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val jwtUtil: JwtUtil,
){

    @DisplayName("signup username valid test")
    @Test
    fun signupTest(){
        val dto = UserDTO(
            userName = "tes",
            password = "12345678"
        )
        val json = ObjectMapper().writeValueAsString(dto)
        println(json)
        mvc.perform(
            post("/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andDo { println("===${it.response.status}") }
            .andExpect(status().isBadRequest)
    }

    @DisplayName("signup password valid test")
    @Test
    fun signupTest2(){
        val dto = UserDTO(
            userName = "minjae",
            password = "11"
        )
        val json = ObjectMapper().writeValueAsString(dto)
        println(json)
        mvc.perform(
            post("/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andDo { println("===${it.response.status}") }
            .andExpect(status().isBadRequest)
    }

    @DisplayName("signup success test")
    @Test
    fun signupTest3(){
        val dto = UserDTO(
            userName = "minjae",
            password = "alswo1234!"
        )
        val json = ObjectMapper().writeValueAsString(dto)
        println(json)
        mvc.perform(
            post("/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andDo { println("===${it.response.status}") }
            .andExpect(status().isOk)
    }

    @DisplayName("login username not found test")
    @Test
    fun loginTest(){
        val dto = UserDTO(
            userName = "tester12",
            password = "12345678"
        )
        val json = ObjectMapper().writeValueAsString(dto)
        mvc.perform(
            post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest)
            .andDo { println(it.response.errorMessage)}
    }

    @DisplayName("login password fail test")
    @Test
    fun loginTest2(){
        val dto = UserDTO(
            userName = "tester",
            password = "12121212"
        )
        val json = ObjectMapper().writeValueAsString(dto)
        mvc.perform(
            post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest)
            .andDo { println(it.response.errorMessage)}
    }

    @DisplayName("login success test")
    @Test
    fun loginTest3(){
        val dto = UserDTO(
            userName = "userName",
            password = "12345678!"
        )
        val json = ObjectMapper().writeValueAsString(dto)
        mvc.perform(
            post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk)
            .andDo { println(it.response.getHeader(jwtUtil.header))}
    }
}