package com.kopring.service

import com.kopring.domain.dto.CommentDTO
import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import com.kopring.domain.entity.Post
import com.kopring.enums.Role
import com.kopring.exceptions.NotFoundPostException
import com.kopring.exceptions.NotOwnerException
import com.kopring.exceptions.UserNotFoundException
import com.kopring.repository.CommentRepository
import com.kopring.repository.PostRepository
import com.kopring.repository.UserRepository
import com.kopring.util.JwtUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

private val postIdDesc = Sort.by(Sort.Direction.DESC, "postId")
@Service
class PostServiceImpl (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val jwtUtil: JwtUtil
) : PostService {
    override fun getPosts(page:Int): Page<PostsDTO> {
        val paging = PageRequest.of(page-1, 10, postIdDesc)
        return postRepository.findAll(paging).map { PostsDTO(it) }
    }

    @Transactional
    override fun savePost(dto: PostDTO, token: String): PostDTO {
        val userName = jwtUtil.getClaims(token).subject
        val requester = userRepository.findById(userName).getOrNull() ?: throw UserNotFoundException()
        return PostDTO(postRepository.save(Post.of(dto, requester.userName)))
    }

    override fun getPost(postId: Long): PostDTO? {
        val post = postRepository.findById(postId).getOrNull() ?: throw NotFoundPostException()
        val postDTO = PostDTO(post)
        post.comments.forEach { postDTO.comments.add(CommentDTO(it)) }
        postDTO.comments.sortBy { it.createDt }
        return postDTO
    }

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

        commentRepository.deleteAllByPostId(post.postId!!)
        postRepository.delete(post)
        return "게시글 삭제 성공"
    }
}