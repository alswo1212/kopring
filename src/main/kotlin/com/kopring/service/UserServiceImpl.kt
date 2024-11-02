package com.kopring.service

import com.kopring.enums.Role
import com.kopring.domain.dto.UserDTO
import com.kopring.domain.entity.User
import com.kopring.exceptions.DuplUsernameException
import com.kopring.exceptions.UserNotFoundException
import com.kopring.repository.UserRepository
import com.kopring.util.JwtUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(
    @Autowired val userRepository: UserRepository,
    @Autowired val jwtUtil: JwtUtil
) : UserService {
    @Transactional
    override fun signup(dto: UserDTO) {
        val user = userRepository.findById(dto.userName).getOrNull()
        if(user != null) throw DuplUsernameException()

        userRepository.save(User(dto.userName, dto.password, Role.USER))
    }

    override fun login(dto: UserDTO, response: HttpServletResponse) {
        val user = userRepository.findById(dto.userName).orElseThrow { UserNotFoundException() }

        if (!user.checkPw(dto.password)) throw UserNotFoundException()
        val token = jwtUtil.createToken(user)
        response.addHeader(jwtUtil.header, "${jwtUtil.prefix}$token")

    }
}