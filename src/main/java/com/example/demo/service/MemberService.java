package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 2020-07-28
 */
@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;


    public Member findOne(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("not found user")
        );
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
