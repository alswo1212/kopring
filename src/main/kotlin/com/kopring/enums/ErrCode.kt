package com.kopring.enums

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

enum class ErrCode (
    val status: HttpStatus,
    val msg:String,
){
    ILLEGAL(HttpStatus.BAD_REQUEST, "요청 정보가 잘못되었습니다."),

    JWT_NON_SECUR(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명입니다."),
    JWT_MALFORM(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명입니다."),
    JWT_EXPIRE(HttpStatus.BAD_REQUEST, "만료된 JWT 서명입니다."),
    JWT_UNSUPPORT(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 서명입니다."),
    JWT_ILLEGAL(HttpStatus.BAD_REQUEST, "잘못된 JWT 서명입니다."),
    JWT_UNNON_FAIL(HttpStatus.BAD_REQUEST, ""),

    NOT_OWNER(HttpStatus.BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다."),
    DUPL_USERNAME(HttpStatus.BAD_REQUEST, "중복된 username 입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),

    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "게시글을 찾을 수 없습니다.")
    ;

    fun toResponse():ResponseEntity<Map<String, String>> = ResponseEntity(mapOf("msg" to msg), status)

}