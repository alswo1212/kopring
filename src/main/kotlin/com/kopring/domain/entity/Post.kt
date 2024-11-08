package com.kopring.domain.entity

import com.kopring.domain.audit.AuditingFields
import com.kopring.domain.dto.CommentDTO
import com.kopring.domain.dto.PostDTO
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy

@Table(
    name = "POSTS",
    indexes = [
        Index(name = "idx_title", columnList = "title"),
        Index(name = "idx_user_name", columnList = "user_name"),
        Index(name = "idx_create_dt", columnList = "create_dt"),
    ]
)
@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId: Long?,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,
    @CreatedBy
    @Column(nullable = false)
    val userName: String,
) : AuditingFields() {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postId")
    lateinit var comments: MutableList<Comment>
    companion object {
        fun of(
            postId: Long?,
            title: String,
            content: String,
            userName: String,
        ): Post = Post(postId, title, content, userName)

        fun of(dto: PostDTO, userName: String) = of(dto.postId, dto.title, dto.content, userName)
    }

    fun update(dto:PostDTO){
        this.title = dto.title
        this.content = dto.content
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        return postId == other.postId
    }

    override fun hashCode(): Int = postId.hashCode()
    override fun toString(): String =
        "Post(postId=$postId, title='$title', content='$content', userId='$userName' ${super.toString()})"

}