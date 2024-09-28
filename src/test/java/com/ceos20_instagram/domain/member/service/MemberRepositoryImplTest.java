package com.ceos20_instagram.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ceos20_instagram.domain.member.MemberRepository;
import com.ceos20_instagram.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

//    @Autowired
//    private EntityManager entityManager;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Test
//    void saveAndFindByIdTest() {
//
//        // given
//        Member member1 = new Member("01012345678", "email1@test.com", "password1", "Name1", "Nickname1");
//        Member member2 = new Member("01087654321", "email2@test.com", "password2", "Name2", "Nickname2");
//        Member member3 = new Member("01011112222", "email3@test.com", "password3", "Name3", "Nickname3");
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//        // when
//        Member foundMember = memberRepository.findById(member1.getId());
//        List<Member> allMembers = memberRepository.findAll();
//
//        // then
//        assertThat(foundMember).isNotNull();
//        assertThat(foundMember.getEmail()).isEqualTo("email1@test.com");
//        assertThat(allMembers.size()).isEqualTo(3);
//    }
//
//    @Test
//    void deleteMemberTest() {
//        // given
//        Member member = new Member("01033334444", "delete@test.com", "deletePass", "DeleteName", "DeleteNick");
//        memberRepository.save(member);
//
//        // when
//        memberRepository.delete(member);
//        List<Member> remainingMembers = memberRepository.findAll();
//
//        // then
//        assertThat(remainingMembers.size()).isEqualTo(0);
//    }
}
