package com.microservice.microserviceshoppingcart.infrastructure.configuration.jwt;

import com.microservice.microserviceshoppingcart.infrastructure.security.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.microservice.microserviceshoppingcart.utils.Constants.AUTH_PREFIX;
import static com.microservice.microserviceshoppingcart.utils.Constants.AUTH_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTH_TOKEN);
        final String jwt;
        final String userName;
        final List<String> roles;
        final Long id;

        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authorizationHeader.substring(7);
        userName = jwtService.extractUsername(jwt);
        roles = jwtService.extractRole(jwt);
        id = jwtService.extractId(jwt);
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            SecurityUser securityUser = new SecurityUser(id,userName);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    securityUser,
                    null,
                    authorities
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request, response);
    }
}
