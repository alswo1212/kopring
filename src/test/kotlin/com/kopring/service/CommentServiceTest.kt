package com.kopring.service

import com.kopring.repository.CommentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentServiceTest @Autowired constructor(
    private val postService: PostService,
    private val commentService: CommentService,
    private val commentRepository: CommentRepository
){
    private val token =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZSIsImlhdCI6MTczMDQyMTk4MH0.G0Qkop_9zyyO7uEQGOfMuTyPbx5_l0R6MamiVcPDkgs"
    @DisplayName("post delete test")
    @Test
    fun postDeleteTest(){
        val postId = 4L
        val count = commentRepository.count()
        postService.removePost(postId, "1234", token)
        println("before count $count")
        val count1 = commentRepository.count()
        assertThat(count).isNotEqualTo(count1)
        println("after count $count1")
    }
}