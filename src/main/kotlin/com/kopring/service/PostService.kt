package com.kopring.service

import com.kopring.domain.dto.PostDTO
import com.kopring.domain.dto.PostsDTO

interface PostService {
    fun getPosts():MutableList<PostsDTO>
    fun savePost(dto:PostDTO):PostDTO
    fun getPost(postId:Long):PostDTO?
    fun modifyPost(postId: Long, dto:PostDTO, token: String):PostDTO?
    fun removePost(postId:Long, pw:String, token:String)
}