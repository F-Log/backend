package com.f_log.flog.repository;

import com.f_log.flog.domain.Member;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUuidAndIsDeletedFalse(UUID uuid);
    List<Member> findByIsDeletedFalse();
}
