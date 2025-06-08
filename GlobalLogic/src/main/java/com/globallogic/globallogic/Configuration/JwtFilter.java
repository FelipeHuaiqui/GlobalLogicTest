package com.globallogic.globallogic.Configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.globallogic.exception.ExceptionModel;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "12345678-TestTecnico";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/login")) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                writeUnauthorizedResponse(response,"Token invalido: se requiere formato bearer" );
                return;
            }

            String token = authHeader.substring(7);

            try {
                Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token);
                request.setAttribute("jwt", token);  //lo mando al controller
            } catch (Exception e) {
                writeUnauthorizedResponse(response,"Token con formato invalido");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    public static void writeUnauthorizedResponse(HttpServletResponse response, String detail) throws IOException {
        ExceptionModel.Error error = new ExceptionModel.Error();
        error.setDetail(detail);
        error.setCodigo(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionModel model = new ExceptionModel();
        model.setError(Collections.singletonList(error));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(model));
    }
}
