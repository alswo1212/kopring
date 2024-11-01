package com.kopring.controller

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import com.kopring.service.PostService
import com.kopring.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException

@RequestMapping("/post")
@RestController
class PostController(
    @Autowired val postService: PostService,
    @Autowired val jwtUtil: JwtUtil
) {
    @GetMapping("/list")
    fun getPosts(): MutableList<PostsDTO> = postService.getPosts()

    @PostMapping
    fun savePost(
        @RequestBody dto: PostDTO,
        request: HttpServletRequest
    ): PostDTO {
        val token = request.getHeader(jwtUtil.header).removePrefix(jwtUtil.prefix)
        jwtUtil.getClaims(token) // 토큰 검사용
        return postService.savePost(dto)
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
        request: HttpServletRequest
    ): PostDTO? {
        val token = request.getHeader(jwtUtil.header).removePrefix(jwtUtil.prefix)
        return postService.getPost(postId)
    }

    @PutMapping("/{postId}")
    fun modifyPost(
        @PathVariable postId: Long,
        @RequestBody dto: PostDTO,
        request: HttpServletRequest
    ): PostDTO? {
        val token = request.getHeader(jwtUtil.header).removePrefix(jwtUtil.prefix)
        return postService.modifyPost(postId, dto, token)
    }

    @DeleteMapping("/{postId}")
    fun removePost(
        @PathVariable postId: Long,
        @RequestParam pw: String,
        request: HttpServletRequest
    ):String {
        val token = request.getHeader(jwtUtil.header).removePrefix(jwtUtil.prefix)
        return postService.removePost(postId, pw, token)
    }
}