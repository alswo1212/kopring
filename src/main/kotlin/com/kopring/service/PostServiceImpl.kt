package com.kopring.service

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import com.kopring.domain.entity.Post
import com.kopring.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class PostServiceImpl(
    @Autowired val postRepository: PostRepository
) : PostService{
    override fun getPosts(): MutableList<PostsDTO> {
        val list = postRepository.findAll().map { PostsDTO(it) }.toMutableList()
        list.sortBy { it.createDt }
        return list
    }

    override fun savePost(dto: PostDTO): PostDTO = PostDTO(postRepository.save(Post.of(dto)))

    override fun getPost(postId: Long): PostDTO? = postRepository.findById(postId).getOrNull()?.let { PostDTO(it) }

    @Transactional
    override fun modifyPost(postId: Long, dto: PostDTO): PostDTO? = postRepository.findById(postId).getOrNull()?.let {
        if (it.pw == dto.pw){
            it.update(dto)
            return PostDTO(it)
        }
        return null
    }

    override fun removePost(postId: Long) = postRepository.findById(postId).ifPresent { postRepository.delete(it) }
}