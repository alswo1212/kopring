package com.kopring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kopring.domain.dto.CommentDTO
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Rollback(false)
class CommentControllerTest @Autowired constructor (
    private val mvc: MockMvc,
    private val jwtUtil: JwtUtil
){
    private val token =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZSIsImlhdCI6MTczMDQyMTk4MH0.G0Qkop_9zyyO7uEQGOfMuTyPbx5_l0R6MamiVcPDkgs"

    @DisplayName("comment save test")
    @Test
    fun commentSaveTest(){
        val dto = CommentDTO(comment = "save test comment", postId = 1L)
        val json = ObjectMapper().writeValueAsString(dto)
        mvc.perform(
            post("/api/comment")
                .header(jwtUtil.header, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk)
            .andDo { println(it.response.contentAsString) }
    }

    @DisplayName("comment update test")
    @Test
    fun commentUpdateTest(){
        val dto = CommentDTO(comment = "update test comment")
        val json = ObjectMapper().writeValueAsString(dto)
        mvc.perform(
            put("/api/comment/2")
                .header(jwtUtil.header, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk)
            .andDo { println(it.response.contentAsString) }
    }

    @DisplayName("comment delete test")
    @Test
    fun commentDeleteTest(){
        mvc.perform(
            delete("/api/comment/3")
                .header(jwtUtil.header, token)
        )
            .andExpect(status().isOk)
            .andDo { println(it.response.contentAsString) }
    }
}