package com.kopring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kopring.domain.dto.PostDTO
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
){

    @DisplayName("[GET] /post/list")
    @Test
    fun test(){
        mvc.perform(get("/post/list"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[GET] /post/{postid}")
    @Test
    fun test2(){
        mvc.perform(get("/post/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[POST] /post")
    @Test
    fun saveTest(){
        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        val dto = PostDTO(
            0,
            "controller save test title",
            "controller save test content",
            "tester",
            "1234",
            LocalDateTime.now()
        )
        val json = mapper.writeValueAsString(dto)
        println("mapper result ================= $json)")

        mvc.perform(get("/post/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[GET] /post/{postId}")
    @Test
    fun getTest(){
        mvc.perform(get("/post/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[PUT] /post({postId}")
    @Test
    fun modifyTest(){
        val dto = PostDTO(
            0,
            "controller test modify title",
            "controller test modify content",
            "tester",
            "1234",
            LocalDateTime.now()
        )
        val json = ObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(dto)

        mvc.perform(put("/post/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }

    @DisplayName("[DELETE] /post/{postId}")
    @Test
    fun removeTest(){
        mvc.perform(delete("/post/1"))
            .andExpect(status().isOk)
            .andDo { println("\n${it.response.status} ${it.response.contentAsString}\n") }
    }


}