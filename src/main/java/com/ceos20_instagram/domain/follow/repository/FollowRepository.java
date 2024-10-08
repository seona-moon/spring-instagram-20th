package com.ceos20_instagram.domain.follow.repository;

import com.ceos20_instagram.domain.chat.entity.ChatRoomMember;
import com.ceos20_instagram.domain.follow.entity.Follow;
import com.ceos20_instagram.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 특정 사용자를 팔로우하는 모든 사용자 조회 (팔로워 리스트)
    List<Follow> findFollowersByFollowing(Member following);

    // 특정 사용자가 팔로우하는 모든 사용자 조회 (팔로잉 리스트)
    List<Follow> findFollowingByFollower(Member follower);

    // 특정 사용자 간의 팔로우 관계 존재 여부 확인
    boolean existsByFollowerAndFollowing(Member follower, Member following);

    Follow findByFollowerAndFollowing(Member follower, Member following);
}
