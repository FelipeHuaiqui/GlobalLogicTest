package com.globallogic.globallogic.Configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtFilterTest {

    private JwtFilter jwtFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        jwtFilter = new JwtFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testSinBearer() throws Exception {
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertTrue(responseWriter.toString().contains("Token invalido"));
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testTokenInvalido() throws Exception {
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertTrue(responseWriter.toString().contains("Token invalido"));
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testTokenMlalformado() throws Exception {
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getHeader("Authorization")).thenReturn("Bearer malformado");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertTrue(responseWriter.toString().contains("Token con formato invalido"));
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testTokenValido() throws Exception {
        String token = io.jsonwebtoken.Jwts.builder()
                .setSubject("emailtest")
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, "12345678-TestTecnico")
                .compact();

        when(request.getRequestURI()).thenReturn("/login");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testNoVaALogin() throws Exception {
        when(request.getRequestURI()).thenReturn("/otro-endpoint");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }
}
