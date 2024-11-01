package com.kopring.handler.exception

import com.kopring.enums.ErrCode
import com.kopring.exceptions.NotFoundCommentException
import com.kopring.exceptions.NotFoundPostException
import com.kopring.exceptions.PostException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PostExceptionHandler {
    @ExceptionHandler(value = [PostException::class])
    fun postExceptionHandle(e:PostException):ResponseEntity<Map<String, String>> = when(e){
        is NotFoundPostException -> ErrCode.NOT_FOUND_POST.toResponse()
        is NotFoundCommentException -> ErrCode.NOT_FOUND_COMMENT.toResponse()
    }
}