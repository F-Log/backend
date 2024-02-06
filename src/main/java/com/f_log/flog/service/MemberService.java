package com.f_log.flog.service;

import com.f_log.flog.domain.Member;
import com.f_log.flog.dto.MemberRequestDto;
import com.f_log.flog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public UUID saveMember(MemberRequestDto memberRequestDto) {
        Member member = Member.from(memberRequestDto);
        UUID uuid = UUID.randomUUID();
        member.setUuid(uuid);
        memberRepository.save(member);
        return uuid;
    }
}
