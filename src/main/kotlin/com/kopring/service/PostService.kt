package com.kopring.service

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO
import org.springframework.data.domain.Page

interface PostService {
    fun getPosts(page:Int):Page<PostsDTO>
    fun savePost(dto:PostDTO):PostDTO
    fun getPost(postId:Long):PostDTO?
    fun modifyPost(postId: Long, dto:PostDTO, token: String):PostDTO?
    fun removePost(postId:Long, pw:String, token:String):String
}