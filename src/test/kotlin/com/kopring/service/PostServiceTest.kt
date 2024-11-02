package com.kopring.service

import com.kopring.domain.dto.PostDTO
import com.kopring.repository.PostRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback

@SpringBootTest
@Rollback(false)
class PostServiceTest (
    @Autowired val postService: PostService,
    @Autowired val postRepository: PostRepository
){
    private val token =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZSIsImlhdCI6MTczMDQyMTk4MH0.G0Qkop_9zyyO7uEQGOfMuTyPbx5_l0R6MamiVcPDkgs"
    @Test
    fun getPostsTest(){
        val count = postRepository.count()
        val posts = postService.getPosts()
        println("count $count posts ${posts.size}")
        assertThat(posts.size).isEqualTo(count)
    }

    @Test
    fun savePostTest(){
        val dto = PostDTO(0, "test title", "test content", "test user name", "1212", null)
        val savePost = postService.savePost(dto)
        assertThat(savePost).isNotNull()
        assertThat(savePost.postId).isNotEqualTo(dto.postId)
    }

    @Test
    fun getPostTest(){
        val post = postService.getPost(1)
        assertThat(post).isNotNull
    }

    @Test
    fun modifyPostTest(){
        val postId = 3L
        val newTitle = "tttt title"

        val dto1 = PostDTO(postId, newTitle, "ttt content", "uuuuser", "pwpw", null)
        val mustNull = postService.modifyPost(postId, dto1, token)
        assertThat(mustNull).isNull()

        val dto2 = PostDTO(postId, newTitle, "ttt content", "uuuuser", "1234", null)
        val mustNotNull = postService.modifyPost(postId, dto2, token)
        assertThat(mustNotNull).isNotNull().hasFieldOrPropertyWithValue("title", newTitle)
    }

    @Test
    fun removePostTest(){
        val postId = 4L
        val post = postService.getPost(postId)!!
        postService.removePost(post.postId!!, "1234", token)
        assertThat(postService.getPost(postId)).isNull()
    }

}