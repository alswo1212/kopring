package com.kopring.domain.dto

import jakarta.validation.constraints.Pattern


data class UserDTO(
    @Pattern(regexp = "^[0-9a-z]{4,10}$") val userName:String,
    @Pattern(regexp = "^\\S{8,15}$") val password:String,
) {
}