package com.ceos20_instagram.domain.follow.service;

import com.ceos20_instagram.domain.follow.repository.FollowRepository;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
public class FollowServiceTest {

    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private MemberService memberService;

    private Member testFollower;
    private Member testFollowing;

//    @BeforeEach
//    void setUp() {
//        testFollower = Member.builder()
//                .id(1L)
//                .phone("123456789")
//                .email("follower@example.com")
//                .password("password")
//                .name("Follower")
//                .nickname("follower_nick")
//                .introduction("I am a follower")
//                .birth(Timestamp.valueOf(LocalDateTime.now()))
//                .gender(Gender.MALE)
//                .profileUrl("http://example.com/follower")
//                .profile_image("http://example.com/follower_image")
//                .build();
//
//        testFollowing = Member.builder()
//                .id(2L)
//                .phone("987654321")
//                .email("following@example.com")
//                .password("password")
//                .name("Following")
//                .nickname("following_nick")
//                .introduction("I am following")
//                .birth(Timestamp.valueOf(LocalDateTime.now()))
//                .gender(Gender.FEMALE)
//                .profileUrl("http://example.com/following")
//                .profile_image("http://example.com/following_image")
//                .build();
//    }
//
//    @Test
//    void 팔로우_성공_테스트() {
//        // given
//        when(memberService.findMemberById(testFollower.getId())).thenReturn(testFollower);
//        when(memberService.findMemberById(testFollowing.getId())).thenReturn(testFollowing);
//        when(followRepository.existsByFollowerAndFollowing(testFollower, testFollowing)).thenReturn(false);
//
//        // when
//        followService.follow(testFollower.getId(), testFollowing.getId());
//
//        // then
//        verify(followRepository, times(1)).save(any(Follow.class));
//    }
//
//    @Test
//    void 팔로우_중복_예외_테스트() {
//        // given
//        when(memberService.findMemberById(testFollower.getId())).thenReturn(testFollower);
//        when(memberService.findMemberById(testFollowing.getId())).thenReturn(testFollowing);
//        when(followRepository.existsByFollowerAndFollowing(testFollower, testFollowing)).thenReturn(true);
//
//        // when & then
//        assertThrows(IllegalArgumentException.class, () -> followService.follow(testFollower.getId(), testFollowing.getId()));
//    }
//
//    @Test
//    void 언팔로우_성공_테스트() {
//        // given
//        when(memberService.findMemberById(testFollower.getId())).thenReturn(testFollower);
//        when(memberService.findMemberById(testFollowing.getId())).thenReturn(testFollowing);
//        when(followRepository.existsByFollowerAndFollowing(testFollower, testFollowing)).thenReturn(true);
//        Follow follow = Follow.builder().follower(testFollower).following(testFollowing).build();
//        when(followRepository.findByFollowerAndFollowing(testFollower, testFollowing)).thenReturn(follow);
//
//        // when
//        followService.unfollow(testFollower.getId(), testFollowing.getId());
//
//        // then
//        verify(followRepository, times(1)).delete(follow);
//    }
}
