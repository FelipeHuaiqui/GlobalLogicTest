package com.globallogic.globallogic.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void generarToken() {
        String email = "test@correocualquiera.com";

        String token = JwtUtil.generarToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        Claims claims = Jwts.parser()
                .setSigningKey("12345678-TestTecnico")
                .parseClaimsJws(token)
                .getBody();

        assertEquals(email, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(new Date()));
    }
}