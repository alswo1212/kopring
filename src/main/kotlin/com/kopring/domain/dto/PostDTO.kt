package com.kopring.domain.dto

import com.kopring.domain.entity.Post
import jakarta.validation.constraints.NotBlank
import java.io.Serializable
import java.time.LocalDateTime

data class PostDTO (
    val postId:Long?,
    @NotBlank
    var title:String,
    @NotBlank
    var content:String,
    var userName:String,
    val createDt: LocalDateTime?
):Serializable{
    val comments: MutableList<CommentDTO> = ArrayList()
    constructor(post: Post): this(post.postId, post.title, post.content, post.userName, post.createDt)
}