package com.kopring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kopring.domain.dto.PostDTO
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@DisplayName("post controller test")

// @Controller 만 테스트
//@WebMvcTest(PostController::class)

// service, repository 포함해서 테스트
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(false)
class PostControllerTest(
    @Autowired val mvc: MockMvc,
    @Autowired val jwtUtil: JwtUtil
) {
    private val token =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZSIsImlhdCI6MTczMDQyMTk4MH0.G0Qkop_9zyyO7uEQGOfMuTyPbx5_l0R6MamiVcPDkgs"

    @DisplayName("[GET] /post/list")
    @Test
    fun test() {
        mvc.perform(
            get("/post/list")
                .param("page", "1")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[GET] /post/{postid}")
    @Test
    fun test2() {
        mvc.perform(
            get("/post/4").header(jwtUtil.header, token)
        ).andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[POST] /post")
    @Test
    fun saveTest() {
        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        val dto = PostDTO(
            0, "controller save test title", "controller save test content", "tester", "1234", LocalDateTime.now()
        )
        val json = mapper.writeValueAsString(dto)
        println("mapper result ================= $json)")

        mvc.perform(
            get("/post/1").contentType(MediaType.APPLICATION_JSON).content(json).header(jwtUtil.header, token)
        ).andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[GET] /post/{postId}")
    @Test
    fun getTest() {
        mvc.perform(
            get("/post/1")
                .header(jwtUtil.header, token)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[PUT] /post({postId}")
    @Test
    fun modifyTest() {
        val dto = PostDTO(
            0, "controller test modify title", "controller test modify content", "tester", "1234", LocalDateTime.now()
        )
        val json = ObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(dto)

        mvc.perform(
            put("/post/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(jwtUtil.header, token)
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.contentType} ${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[DELETE] /post/{postId}")
    @Test
    fun removeTest() {
        mvc.perform(
            delete("/post/1")
                .param("pw", "1234")
                 .header(jwtUtil.header, token)
        ).andExpect(status().isOk)
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("작성자가 아닌 사람이 지울때 test")
    @Test
    fun notOwnerTest(){
        val userDTO = UserDTO(
            userName = "tester",
            password = "12345678"
        )
        val userJson = ObjectMapper().writeValueAsString(userDTO)
        var userToken = ""

        mvc.perform(
            post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isOk)
            .andDo { userToken = it.response.getHeader(jwtUtil.header)!! }
        println("token is $userToken")
        mvc.perform(
            delete("/post/6")
                .param("pw", "1234")
                .header(jwtUtil.header, userToken)
        ).andExpect(status().isBadRequest)
            .andDo { println(it.response.contentAsString) }
    }

    @DisplayName("jwt file test")
    @Test
    fun jwtFialTest(){
        mvc.perform(
            delete("/post/6")
                .param("pw", "1234")
                .header(jwtUtil.header, "Bearer kkk")
        ).andExpect(status().isBadRequest)
            .andDo { println(it.response.contentAsString) }
    }

}