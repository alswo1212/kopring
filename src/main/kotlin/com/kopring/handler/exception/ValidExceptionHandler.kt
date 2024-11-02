package com.kopring.handler.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.method.MethodValidationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidExceptionHandler {
    @ExceptionHandler(MethodValidationException::class)
    fun validExceptionHandle(e:MethodValidationException):ResponseEntity<Map<String, String>>{
        return ResponseEntity(mapOf("msg" to (e.message ?: "")), HttpStatus.BAD_REQUEST)
    }
}