package com.kopring.controller

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import com.kopring.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/post")
@RestController
class PostController (
    @Autowired val postService: PostService
){
    @GetMapping("/list")
    fun getPosts():MutableList<PostsDTO> = postService.getPosts()
    @PostMapping
    fun savePost(@RequestBody dto: PostDTO):PostDTO = postService.savePost(dto)
    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId:Long):PostDTO? = postService.getPost(postId)

    @PutMapping("/{postId}")
    fun modifyPost(@PathVariable postId: Long, @RequestBody dto:PostDTO):PostDTO? = postService.modifyPost(postId, dto)
    @DeleteMapping("/{postId}")
    fun removePost(@PathVariable postId:Long) = postService.removePost(postId)
}