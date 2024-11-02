package com.kopring.repository

import com.kopring.domain.entity.Comment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback

@DataJpaTest
@Rollback(false)
class CommentRepositoryTest constructor(
    private val commentRepository: CommentRepository,
){
    @DisplayName("save test")
    @Test
    fun saveTest(){
        val comment = Comment(
            id = 0L,
            userName = "userName",
            postId = 4L,
            comment = "comment"
        )

        val count = commentRepository.count()
        commentRepository.save(comment)
        assertThat(count).isEqualTo(commentRepository.count() - 1)
    }
}