package com.f_log.flog.service;

import com.f_log.flog.domain.Member;
import com.f_log.flog.dto.MemberMapper;
import com.f_log.flog.dto.MemberRequestDto;
import com.f_log.flog.dto.MemberResponseDto;
import com.f_log.flog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Transactional
    public UUID saveMember(MemberRequestDto memberRequestDto) {
        Member member = memberMapper.fromDto(memberRequestDto);
        UUID uuid = UUID.randomUUID();
        member.setUuid(uuid);
        memberRepository.save(member);
        return uuid;
    }

    @Transactional
    public MemberResponseDto getMemberByUuid(UUID uuid) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(uuid);
        if (member != null) {
            return memberMapper.toDto(member);
        } else {
            return null;
        }
    }


    @Transactional
    public List<MemberResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findByIsDeletedFalse();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for (Member member : members) {
            memberResponseDtos.add(memberMapper.toDto(member));
        }
        return memberResponseDtos;
    }

    @Transactional
    public boolean updateMember(UUID uuid, MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(uuid);
        if (member != null) {
            memberMapper.updateFromDto(member, memberRequestDto);
            memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteMember(UUID uuid) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(uuid);
        if (member != null) {
            member.setDeleted();
            memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }
}
