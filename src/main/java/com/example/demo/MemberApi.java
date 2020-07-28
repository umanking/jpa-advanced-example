package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2020-07-28
 */
@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRepository memberRepository;

    @PostMapping("/api/v1/member")
    public CreateMemberResponse createMemberV1(@RequestBody @Validated Member member) {
        return new CreateMemberResponse(memberRepository.save(member).getId());
    }

    @PostMapping("/api/v2/member")
    public CreateMemberResponse createMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        return new CreateMemberResponse(memberRepository.save(member).getId());
    }

    @Data
    static class CreateMemberRequest {
        String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
