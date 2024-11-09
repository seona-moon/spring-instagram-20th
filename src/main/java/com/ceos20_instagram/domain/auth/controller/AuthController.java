package com.ceos20_instagram.domain.auth.controller;

import com.ceos20_instagram.domain.auth.dto.UserLoginDto;
import com.ceos20_instagram.domain.auth.entity.CustomUserDetails;
import com.ceos20_instagram.domain.auth.service.CustomUserDetailService;
import com.ceos20_instagram.domain.member.entity.UserRole;
import com.ceos20_instagram.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class AuthController {

    private final CustomUserDetailService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserLoginDto userLoginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String role = authentication.getAuthorities()
                                    .iterator()
                                    .next()
                                    .getAuthority();

        UserRole userRole = UserRole.fromString(role);
        String token = tokenProvider.createAccessToken(authentication.getName(), authentication, userRole);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        String authorities = customUserDetails.getAuthorities()
                                              .toString();

        return ResponseEntity.ok("Username: " + username + ", Authorities: " + authorities);
    }

}