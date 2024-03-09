package com.f_log.flog.member.dto;

import com.f_log.flog.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public MemberResponseDto toDto(Member member) {
        return MemberResponseDto.builder()
                .uuid(member.getUuid())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .name(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .build();
    }

    public Member fromDto(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .loginId(memberRequestDto.getLoginId())
                .password(memberRequestDto.getPassword())
                .name(memberRequestDto.getName())
                .gender(memberRequestDto.getGender())
                .age(memberRequestDto.getAge())
                .build();
    }

    public void updateFromDto(Member member, MemberRequestDto memberRequestDto) {
        member.updateMember(
                memberRequestDto.getLoginId(),
                memberRequestDto.getPassword(),
                memberRequestDto.getName(),
                memberRequestDto.getGender(),
                memberRequestDto.getAge()
        );
    }
}
