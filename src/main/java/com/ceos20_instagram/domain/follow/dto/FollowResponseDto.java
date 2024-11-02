package com.ceos20_instagram.domain.follow.dto;

import com.ceos20_instagram.domain.follow.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponseDto {
    private Long id;
    private Long followerId;
    private Long followingId;

    // Follow 엔티티를 FollowResponseDto로 변환하는 정적 메서드
    public static FollowResponseDto from(Follow follow) {
        return FollowResponseDto.builder()
                                .id(follow.getId())
                                .followerId(follow.getFollower()
                                                  .getId())
                                .followingId(follow.getFollowing()
                                                   .getId())
                                .build();
    }
}
