package com.vmuller.github.desafiozappts.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmuller.github.desafiozappts.data.PlayerDetail;
import com.vmuller.github.desafiozappts.entities.Player;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRATION = 600000;

    public static final String TOKEN_PASSWORD = "d1834680-d40d-11ec-9d64-0242ac120002";
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            Player player = new ObjectMapper().readValue(request.getInputStream(), Player.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               player.getEmail(), player.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("User login failed. " , e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PlayerDetail playerDetail = (PlayerDetail)authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(playerDetail.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));

        response.getWriter().write(token);
        response.getWriter().flush();

    }
}
