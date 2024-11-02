package com.kopring.util

import com.kopring.domain.entity.User
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil (
    @Value("\${jwt.secret-key}")
    private val secretKey:String
){
    val header: String = "Authorization"
    val prefix = "Bearer "
    private val auth_key = "auth"
    private val algorithm = SignatureAlgorithm.HS256
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    private fun jwtParser():JwtParser = Jwts.parserBuilder().setSigningKey(key).build()
    fun createToken(user: User):String{
        val now = Date()
        return Jwts.builder()
            .setSubject(user.userName)
            .claim(auth_key, user.role)
            .setIssuedAt(now)
            .signWith(key, algorithm)
            .compact()
    }

    fun getClaims(token: String) = jwtParser().parseClaimsJws(token).body
}