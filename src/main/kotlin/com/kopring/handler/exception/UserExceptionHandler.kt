package com.kopring.handler.exception

import com.kopring.enums.ErrCode
import com.kopring.exceptions.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {
    @ExceptionHandler(value = [UserException::class])
    fun userExceptionHandle(e: UserException):ResponseEntity<Map<String, String>>{
        return when(e){
            is NotOwnerException -> ErrCode.NOT_OWNER.toResponse()
            is DuplUsernameException -> ErrCode.DUPL_USERNAME.toResponse()
            is UserNotFoundException -> ErrCode.NOT_FOUND_USER.toResponse()
            is UnknowUserException -> ErrCode.UNKNOWN_USER.toResponse()
        }
    }
}