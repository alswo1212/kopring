package com.kopring.domain.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class UserDTO(
    @field:NotBlank
    @field:Pattern(
        regexp = "^[0-9a-z]{4,10}$",
        message = "유저명은 소문자 알파벳과 숫자로 최소 4글자에서 10글자 입니다."
    )
    val userName:String,
    @field:NotBlank
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,15}\$\n",
        message = "비밀번호는 영어 대소문자, 숫자, 특수문자를 사용해 8 ~ 15글자 입니다."
    )
    val password:String,
) {
}