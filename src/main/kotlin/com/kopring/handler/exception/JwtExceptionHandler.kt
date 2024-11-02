package com.kopring.handler.exception

import com.kopring.enums.ErrCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.naming.AuthenticationException

@RestControllerAdvice
class JwtExceptionHandler {
    @ExceptionHandler(value =[
            SecurityException::class,
            MalformedJwtException::class,
            ExpiredJwtException::class,
            UnsupportedJwtException::class,
            IllegalArgumentException::class,
            AuthenticationException::class,
        ]
    )
    fun jwtExceptionHandle(e:Exception):ResponseEntity<Map<String, String>>{
        return when(e){
            is SecurityException -> ErrCode.JWT_NON_SECUR.toResponse()
            is MalformedJwtException -> ErrCode.JWT_MALFORM.toResponse()
            is ExpiredJwtException -> ErrCode.JWT_EXPIRE.toResponse()
            is UnsupportedJwtException -> ErrCode.JWT_UNSUPPORT.toResponse()
            is IllegalArgumentException -> ErrCode.JWT_ILLEGAL.toResponse()
            is AuthenticationException -> ErrCode.JWT_NOT_EXIST.toResponse()
            else -> ErrCode.JWT_NON_SECUR.toResponse()
        }
    }
}