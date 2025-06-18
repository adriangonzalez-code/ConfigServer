package com.driagon.services.configserver.filters;

import com.driagon.services.configserver.services.IScopeKeyAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScopeKeyAuthFilter extends OncePerRequestFilter {

    private final IScopeKeyAuthService scopeKeyAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String scope = request.getHeader("X-CONFIG-SCOPE");
        String accessKey = request.getHeader("X-CONFIG-KEY");

        if (scope != null && accessKey != null) {
            if (scopeKeyAuthService.isValid(scope, accessKey)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(scope, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid scope or access key");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}