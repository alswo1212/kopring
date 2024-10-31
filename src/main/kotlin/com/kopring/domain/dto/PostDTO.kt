package com.kopring.domain.dto

import com.kopring.domain.entity.Post
import java.io.Serializable
import java.time.LocalDateTime

data class PostDTO (
    val postId:Long?,
    var title:String,
    var content:String,
    var userName:String,
    val pw:String,
    val createDt: LocalDateTime?
):Serializable{
    constructor(post: Post): this(post.postId, post.title, post.content, post.userName, post.pw, post.createDt)
}