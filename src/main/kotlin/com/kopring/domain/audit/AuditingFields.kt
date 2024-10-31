package com.kopring.domain.audit

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Builder.Default
import lombok.ToString
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Protected

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class AuditingFields {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createDt: LocalDateTime = LocalDateTime.now()
        protected set

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    var modifyDt: LocalDateTime = LocalDateTime.now()
        protected set

    override fun toString(): String = "AuditingFields(createDt=$createDt, modifyDt=$modifyDt)"

}