package com.kopring.service

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import com.kopring.domain.entity.Post
import com.kopring.enums.Role
import com.kopring.exceptions.NotFoundPostException
import com.kopring.exceptions.NotOwnerException
import com.kopring.exceptions.UserNotFoundException
import com.kopring.repository.PostRepository
import com.kopring.repository.UserRepository
import com.kopring.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class PostServiceImpl(
    @Autowired val postRepository: PostRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val jwtUtil: JwtUtil
) : PostService {
    override fun getPosts(): MutableList<PostsDTO> {
        val list = postRepository.findAll().map { PostsDTO(it) }.toMutableList()
        list.sortBy { it.createDt }
        return list
    }

    @Transactional
    override fun savePost(dto: PostDTO): PostDTO = PostDTO(postRepository.save(Post.of(dto)))

    override fun getPost(postId: Long): PostDTO? = postRepository.findById(postId).getOrNull()?.let { PostDTO(it) }

    @Transactional
    override fun modifyPost(
        postId: Long,
        dto: PostDTO,
        token: String,
        ): PostDTO? {
        val userName = jwtUtil.getClaims(token).subject
        val requester = userRepository.findById(userName).getOrNull() ?: throw UserNotFoundException()
        val post = postRepository.findById(postId).getOrNull() ?: throw NotFoundPostException()
        if (requester.role != Role.ADMIN && userName != post.userName) throw NotOwnerException()

        post.update(dto)
        return PostDTO(post)
    }

    @Transactional
    override fun removePost(postId: Long, pw: String, token: String):String {
        val userName = jwtUtil.getClaims(token).subject
        val requester = userRepository.findById(userName)
            .getOrNull() ?: throw UserNotFoundException()
        val post = postRepository.findById(postId).getOrNull() ?: throw NotFoundPostException()
        if (requester.role != Role.ADMIN && userName != post.userName) throw NotOwnerException()
        postRepository.delete(post)
        return "게시글 삭제 성공"
    }
}