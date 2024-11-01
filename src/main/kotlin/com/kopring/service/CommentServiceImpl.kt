package com.kopring.service

import com.kopring.domain.dto.CommentDTO
import com.kopring.domain.entity.Comment
import com.kopring.exceptions.NotFoundCommentException
import com.kopring.exceptions.UnknowUserException
import com.kopring.repository.CommentRepository
import com.kopring.repository.UserRepository
import com.kopring.util.JwtUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
) : CommentService {
    @Transactional
    override fun save(dto: CommentDTO, token: String): CommentDTO {
        val userName = jwtUtil.getClaims(token).subject
        userRepository.findById(userName).getOrNull() ?: throw UnknowUserException()

        val entity = Comment(0L, dto.comment, dto.postId, userName)
        return CommentDTO(commentRepository.save(entity))
    }

    @Transactional
    override fun modify(id: Long, dto: CommentDTO, token: String): CommentDTO {
        val userName = jwtUtil.getClaims(token).subject
        userRepository.findById(userName).getOrNull() ?: throw UnknowUserException()

        val entity = commentRepository.findById(id).getOrNull() ?: throw NotFoundCommentException()
        entity.update(dto)

        return CommentDTO(entity)
    }

    @Transactional
    override fun remove(id: Long, token: String): String {
        val userName = jwtUtil.getClaims(token).subject
        userRepository.findById(userName).getOrNull() ?: throw UnknowUserException()
        val comment = commentRepository.findById(id).getOrNull() ?: throw NotFoundCommentException()
        commentRepository.delete(comment)
        return "댓글 삭제 성공"
    }
}