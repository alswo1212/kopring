package com.kopring.domain.dto

import com.kopring.domain.entity.Comment
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class CommentDTO(
    val id: Long? = null,
    @field:NotBlank
    val comment:String,
    val userName:String? = null,
    val createDt:LocalDateTime? = null,
    val postId:Long = 0L,
) {
    constructor(comment: Comment): this(comment.id, comment.comment, comment.userName, comment.createDt, comment.postId)
}