package com.f_log.flog.member.service;

import com.f_log.flog.global.exception.MemberNotFoundException;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.dto.MemberLoginResponseDto;
import com.f_log.flog.member.dto.MemberMapper;
import com.f_log.flog.member.dto.MemberRequestDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.repository.MemberRepository;
import com.f_log.flog.member.util.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        try {
            Member member = memberMapper.fromDto(memberRequestDto);
            String hashedPassword = PasswordUtils.hashPassword(memberRequestDto.getPassword());
            member.setPassword(hashedPassword);
            memberRepository.save(member);
            return member.getUuid();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public MemberLoginResponseDto login(String loginId, String password, HttpSession session) {
        Member member = memberRepository.findByLoginIdAndIsDeletedFalse(loginId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with login ID: " + loginId));

        try {
            String hashedPassword = PasswordUtils.hashPassword(password);
            if(hashedPassword.equals(member.getPassword())) {
                session.setAttribute("member", member);
                return new MemberLoginResponseDto(member.getUuid(), member.getLoginId());
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @Transactional
    public MemberResponseDto getMemberByUuid(UUID uuid) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(uuid);
        if (member != null) {
            return memberMapper.toDto(member);
        } else {
            throw new MemberNotFoundException("Member not found with UUID: " + uuid);
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
