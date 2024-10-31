package com.kopring.domain.dto

import com.kopring.domain.entity.Post
import java.time.LocalDateTime

data class PostsDTO(
    val postId:Long,
    var title:String,
    val createDt: LocalDateTime,
    val userName:String
){
    constructor(post:Post):this(post.postId!!, post.title, post.createDt, post.userName)
}