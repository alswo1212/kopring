package com.kopring.service

import com.kopring.domain.dto.UserDTO
import jakarta.servlet.http.HttpServletResponse

interface UserService {
    fun signup(dto: UserDTO)
    fun login(dto: UserDTO, response: HttpServletResponse)
}