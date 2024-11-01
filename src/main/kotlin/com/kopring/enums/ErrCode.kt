package com.kopring.enums

import org.springframework.http.HttpStatus

enum class ErrCode (
    val status: HttpStatus,
    val msg:String
){
    ILLEGAL(HttpStatus.BAD_REQUEST, "잘못된 정보입니다."),

}