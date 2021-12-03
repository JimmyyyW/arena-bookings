package com.cwagritech.arenabookings.config

import com.cwagritech.arenabookings.model.User
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*


@Component
class JwtTokenUtil {
    private val jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP"
    private val jwtIssuer = "cwagritech.io"
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenUtil::class.java)
    fun generateAccessToken(user: User): String {
        return Jwts.builder()
            .setSubject("${user.id},${user.username}")
                //format("%s,%s", user.id, user.username))
            .setIssuer(jwtIssuer)
            .setIssuedAt(Date())
            .setExpiration(Date((System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000) / 7  * 365)) // 1 week * 52 (1y)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserId(token: String?): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",")[0]
    }

    fun getUsername(token: String?): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",")[1]
    }

    fun getExpirationDate(token: String?): Date {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.expiration
    }

    fun validate(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature - {}", ex.message)
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token - {}", ex.message)
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token - {}", ex.message)
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token - {}", ex.message)
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty - {}", ex.message)
        }
        return false
    }
}