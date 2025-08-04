package com.rodolfoalves.todosimple.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodolfoalves.todosimple.exceptions.GlobalExceptionHandler;
import com.rodolfoalves.todosimple.models.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new GlobalExceptionHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User userCredentials = new ObjectMapper().readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>()
            );

            Authentication authentication = this.authenticationManager.authenticate(authToken);
            return authentication;
        } catch (Exception error) {
            throw new RuntimeException();
        }
    }

    protected void sucessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication) throws IOException, ServletException {

        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) authentication.getPrincipal();
        String username = ((UserSpringSecurity) authentication.getPrincipal()).getUsername();

        String token = this.jwtUtil.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }
}
