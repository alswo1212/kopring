package com.kopring.repository

import com.kopring.domain.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<Comment, Long> {
    @Modifying
    @Query("delete from Comment c where c.postId = :postId")
    fun deleteAllByPostId(postId:Long)
}