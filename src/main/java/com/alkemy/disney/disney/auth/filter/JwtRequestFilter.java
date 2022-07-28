package com.alkemy.disney.disney.auth.filter;

import com.alkemy.disney.disney.auth.services.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter
{
    // Authenticates the passed Auth object, returns a fully populated Auth object if successful
    private AuthenticationManager authenticationManager;

    public JwtRequestFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override // Jwt Filter for Requests and Token Authentication
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/auth/refresh")) {
            filterChain.doFilter(request, response);
        }

        else {
            String authenticationHeader = request.getHeader("Authorization");

            if(authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {

                try {
                    String token = authenticationHeader.substring("Bearer ".length());
                    String username = JwtUtils.decodeToken(token);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                } catch(Exception e) {
                    filterChain.doFilter(request, response);
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
