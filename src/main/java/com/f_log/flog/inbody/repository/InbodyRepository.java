package com.f_log.flog.inbody.repository;

import com.f_log.flog.inbody.domain.Inbody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface InbodyRepository extends JpaRepository<Inbody, UUID> {
    List<Inbody> findAllByMemberUuidAndIsDeletedFalse(UUID memberUuid);
    Inbody findByInbodyUuidAndIsDeletedFalse(UUID inbodyUuid);
}
