package com.kopring.domain.entity

import com.kopring.domain.audit.AuditingFields
import com.kopring.domain.dto.CommentDTO
import jakarta.persistence.*
import lombok.ToString

@ToString
@Entity
@Table(name = "COMMENTS",
    indexes = [
    Index(name = "idx_comment_post_id", columnList = "post_id")
])
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var comment:String,
    @Column(nullable = false)
    val postId:Long,
    @Column(nullable = false)
    val userName:String,
) :AuditingFields(){


    fun update(dto: CommentDTO){
        this.comment = dto.comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}