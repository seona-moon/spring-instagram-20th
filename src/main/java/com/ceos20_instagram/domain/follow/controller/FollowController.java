package com.ceos20_instagram.domain.follow.controller;

import com.ceos20_instagram.domain.follow.dto.FollowRequestDto;
import com.ceos20_instagram.domain.follow.dto.FollowResponseDto;
import com.ceos20_instagram.domain.follow.service.FollowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 팔로우 생성
    @PostMapping
    public ResponseEntity<FollowResponseDto> follow(@RequestBody FollowRequestDto dto) {
        try {
            FollowResponseDto followResponse = followService.follow(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(followResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null);
        }
    }

    // 팔로우 삭제 (언팔로우)
    @DeleteMapping
    public ResponseEntity<Void> unfollow(@RequestBody FollowRequestDto dto) {
        try {
            followService.unfollow(dto);
            return ResponseEntity.noContent()
                                 .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .build();
        }
    }

    // 특정 사용자의 팔로워 조회
    @GetMapping("/followers/{memberId}")
    public ResponseEntity<List<FollowResponseDto>> getFollowers(@PathVariable Long memberId) {
        List<FollowResponseDto> followers = followService.getFollowers(memberId);
        return ResponseEntity.ok(followers);
    }

    // 특정 사용자의 팔로잉 조회
    @GetMapping("/followings/{memberId}")
    public ResponseEntity<List<FollowResponseDto>> getFollowings(@PathVariable Long memberId) {
        List<FollowResponseDto> followings = followService.getFollowings(memberId);
        return ResponseEntity.ok(followings);
    }
}
