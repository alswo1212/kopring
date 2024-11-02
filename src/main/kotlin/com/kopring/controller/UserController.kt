package com.kopring.controller

import com.kopring.domain.dto.UserDTO
import com.kopring.service.UserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController (
    @Autowired val userService: UserService
){
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody dto:UserDTO):String{
        println(dto)
        userService.signup(dto)
        return "회원가입 성공"
    }

    @PostMapping("/login")
    fun login(@RequestBody dto: UserDTO, response: HttpServletResponse):String{
        userService.login(dto, response)
        return "로그인 성공"
    }



}