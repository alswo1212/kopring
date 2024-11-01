package com.kopring.service

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import com.kopring.domain.entity.Post
import com.kopring.enums.ErrCode
import com.kopring.enums.Role
import com.kopring.repository.PostRepository
import com.kopring.repository.UserRepository
import com.kopring.util.JwtUtil
import jdk.jshell.spi.ExecutionControl.UserException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.file.attribute.UserPrincipalNotFoundException
import kotlin.jvm.optionals.getOrElse
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
        val requester = userRepository.findById(userName).getOrNull() ?: throw UserPrincipalNotFoundException("not found $userName")
        return postRepository.findById(postId).getOrNull()?.let {
            if (requester.role != Role.ADMIN && (userName != it.userName || it.pw != dto.pw))
                return null

            it.update(dto)
            return PostDTO(it)
        }
    }

    @Transactional
    override fun removePost(postId: Long, pw: String, token: String) {
        val userName = jwtUtil.getClaims(token).subject
        val requester =
            userRepository.findById(userName).getOrNull() ?: throw UserPrincipalNotFoundException("not found $userName")
        postRepository.findById(postId).ifPresentOrElse(
            {
                if (requester.role != Role.ADMIN && (userName != it.userName || it.pw != pw))
                    throw IllegalArgumentException(ErrCode.ILLEGAL.msg)
                postRepository.delete(it)
            },
            { throw IllegalArgumentException(ErrCode.ILLEGAL.msg) }
        )
    }
}