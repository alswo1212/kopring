package com.kopring.controller

import com.kopring.domain.dto.CommentDTO
import com.kopring.service.CommentService
import com.kopring.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/comment")
class CommentController (
    private val commentService: CommentService,
    private val jwtUtil: JwtUtil,
){
    @PostMapping
    fun saveComment(@RequestBody dto:CommentDTO, request: HttpServletRequest):CommentDTO {
        val token = jwtUtil.getToken(request)
        return commentService.save(dto, token)
    }

    @PutMapping("/{id}")
    fun modifyComment(
        @PathVariable id:Long,
        @RequestBody dto: CommentDTO,
        request: HttpServletRequest
    ):CommentDTO{
        val token = jwtUtil.getToken(request)
        return commentService.modify(id, dto, token)
    }

    @DeleteMapping("/{id}")
    fun removeComment(
        @PathVariable id:Long,
        request: HttpServletRequest
    ):String{
        val token = jwtUtil.getToken(request)
        return commentService.remove(id, token)
    }

}