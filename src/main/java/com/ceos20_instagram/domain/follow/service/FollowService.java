package com.ceos20_instagram.domain.follow.service;

import com.ceos20_instagram.domain.follow.dto.FollowRequestDto;
import com.ceos20_instagram.domain.follow.dto.FollowResponseDto;
import com.ceos20_instagram.domain.follow.entity.Follow;
import com.ceos20_instagram.domain.follow.repository.FollowRepository;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberService memberService;

    // 팔로우 생성
    public FollowResponseDto follow(FollowRequestDto dto) {
        Member follower = memberService.findMemberById(dto.getFollowerId());
        Member following = memberService.findMemberById(dto.getFollowingId());

        // 이미 팔로우 관계가 있는지 확인
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalArgumentException("이미 팔로우하고 있습니다.");
        }

        // Follow 엔티티 생성 및 저장
        Follow follow = Follow.builder()
                              .follower(follower)
                              .following(following)
                              .build();
        Follow savedFollow = followRepository.save(follow);

        return FollowResponseDto.from(savedFollow);
    }

    // 팔로우 삭제 (언팔로우)
    public void unfollow(FollowRequestDto dto) {
        Member follower = memberService.findMemberById(dto.getFollowerId());
        Member following = memberService.findMemberById(dto.getFollowingId());

        // 팔로우 관계 찾기
        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalArgumentException("팔로우 관계가 아닙니다.");
        }
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following);
        followRepository.delete(follow);
    }

    // 특정 사용자의 팔로워 조회
    public List<FollowResponseDto> getFollowers(Long memberId) {
        Member following = memberService.findMemberById(memberId);
        List<Follow> followers = followRepository.findFollowersByFollowing(following);

        return followers.stream()
                        .map(FollowResponseDto::from)
                        .collect(Collectors.toList());
    }

    // 특정 사용자의 팔로잉 조회
    public List<FollowResponseDto> getFollowings(Long memberId) {
        Member follower = memberService.findMemberById(memberId);
        List<Follow> followings = followRepository.findFollowingByFollower(follower);

        return followings.stream()
                         .map(FollowResponseDto::from)
                         .collect(Collectors.toList());
    }
}
