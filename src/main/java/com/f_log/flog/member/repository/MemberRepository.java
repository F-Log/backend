package com.f_log.flog.member.repository;

import com.f_log.flog.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUuidAndIsDeletedFalse(UUID uuid);
    List<Member> findByIsDeletedFalse();
}
