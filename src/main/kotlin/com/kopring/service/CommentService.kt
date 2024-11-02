package com.kopring.service

import com.kopring.domain.dto.CommentDTO

interface CommentService {
    fun save(dto: CommentDTO, token: String): CommentDTO
    fun modify(id: Long, dto: CommentDTO, token: String): CommentDTO
    fun remove(id: Long, token: String): String
}