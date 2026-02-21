package com.BankManagmentSystem.configurations;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // ✅ VERY IMPORTANT FIX
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return request.getServletPath().startsWith("/auth");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                String email = jwtService.extractUsername(token);

                if (email != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }

            } catch (Exception e) {
                // If token invalid → just continue
                // Do NOT block login
            }
        }

        filterChain.doFilter(request, response);
    }
}
