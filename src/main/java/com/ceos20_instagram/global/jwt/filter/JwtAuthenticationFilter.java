package com.ceos20_instagram.global.jwt.filter;

import com.ceos20_instagram.domain.auth.dto.UserLoginDto;
import com.ceos20_instagram.domain.member.entity.UserRole;
import com.ceos20_instagram.global.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenProvider tokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginDto loginDto = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication authResult) {
        final String username = authResult.getName();

        String role = authResult.getAuthorities()
                                .iterator()
                                .next()
                                .getAuthority();

        UserRole userRole = UserRole.fromString(role);

        String accessToken = tokenProvider.createAccessToken(username, authResult, userRole);

        response.addHeader("Authorization", "Bearer " + accessToken);

        SecurityContextHolder.getContext()
                             .setAuthentication(authResult);
    }

    @Override
    public void setFilterProcessesUrl(String loginUrl) {
        super.setFilterProcessesUrl("api/users/login");
    }
}