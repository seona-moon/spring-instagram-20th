package com.ceos20_instagram.domain.auth.service;

import com.ceos20_instagram.domain.auth.entity.CustomUserDetails;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.repository.MemberRepository;
import com.ceos20_instagram.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Member member = memberRepository.findMemberByEmail(username) // username == email
                                              .orElseThrow(
                                                      () -> new UsernameNotFoundException("Not Found: " + username));

        return new CustomUserDetails(member);
    }

}
