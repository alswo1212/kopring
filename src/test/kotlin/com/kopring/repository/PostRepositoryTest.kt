package com.kopring.repository

import com.kopring.config.JpaConfig
import com.kopring.domain.entity.Post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import


@Import(JpaConfig::class)
@DisplayName("JPA Post Repository Test")
@DataJpaTest
class PostRepositoryTest (
    @Autowired val postRepository: PostRepository
){
    @DisplayName("save test")
    @Test
    fun saveTest(){
        var post = Post(0, "title", "content", "tester")
        println("before save === $post")
        post = postRepository.save(post)
        println("after save === $post")
    }

    @DisplayName("find all Test")
    @Test
    fun findAllTest(){
        val findAll = postRepository.findAll()
        findAll.forEach { println(it) }
    }

    @DisplayName("insert Test")
    @Test
    fun insertTest(){
        val count = postRepository.count()
        val post = Post(0, "insert Title", "insert content", "1")
        postRepository.save(post)
//        println("before $count after ${postRepository.count()}")
        assertThat(count + 1 == postRepository.count()).isTrue()
    }

    @DisplayName("update Test")
    @Test
    fun updateTest(){
        val post = postRepository.findById(1L).orElseThrow()
        val nextContent = "update Test"
        println(post.content)
        post.content = nextContent

        val save = postRepository.saveAndFlush(post)

        assertThat(save).hasFieldOrPropertyWithValue("content", nextContent)
        println(save.content)
    }

    @DisplayName("delete Test")
    @Test
    fun deleteTest(){
        val post = postRepository.findById(1L).orElseThrow()
        val count = postRepository.count()
        postRepository.delete(post)
        assertThat(postRepository.count()).isEqualTo(count - 1)
    }
}