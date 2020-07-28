package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 2020-07-28
 */
@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> findMembersV1() {
        return memberRepository.findAll();
    }

    @GetMapping("/api/v2/members")
    public Result<MemberDto> findMembersV2() {
        List<Member> allMembers = memberRepository.findAll();
        List<MemberDto> collect = allMembers.stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());

        return new Result(collect, collect.size());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        T data;
        int count;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        String name;
    }


    @PostMapping("/api/v1/members")
    public CreateMemberResponse createMemberV1(@RequestBody @Validated Member member) {
        return new CreateMemberResponse(memberRepository.save(member).getId());
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse createMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        return new CreateMemberResponse(memberRepository.save(member).getId());
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable Long id, @RequestBody UpdateMemberRequest request) {
        // command와 query 구분
        // update를 하고 반환하지 않고, 새롭게 조회한다.
        memberService.update(id, request.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(), member.getName());
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

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        Long id;
        String name;
    }

    @Data
    static class UpdateMemberRequest {
        String name;
    }
}
