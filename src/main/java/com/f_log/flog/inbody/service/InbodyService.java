package com.f_log.flog.inbody.service;

import com.f_log.flog.inbody.domain.Inbody;
import com.f_log.flog.inbody.dto.InbodyMapper;
import com.f_log.flog.inbody.dto.InbodyRequestDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbody.repository.InbodyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InbodyService {

    private final InbodyRepository inbodyRepository;
    private final InbodyMapper inbodyMapper;

    @Autowired
    public InbodyService(InbodyRepository inbodyRepository, InbodyMapper inbodyMapper) {
        this.inbodyRepository = inbodyRepository;
        this.inbodyMapper = inbodyMapper;
    }

    public InbodyResponseDto createInbody(InbodyRequestDto requestDto) {
        Inbody inbody = inbodyMapper.toEntity(requestDto);
        Inbody savedInbody = inbodyRepository.save(inbody);
        return inbodyMapper.toDto(savedInbody);
    }

    public InbodyResponseDto getLatestInbodyOfMember(UUID memberUuid) {
        Inbody inbody = inbodyRepository.findTopByMemberUuidOrderByCreatedAtDesc(memberUuid).orElse(null);
        return inbodyMapper.toDto(inbody);
    }

    public InbodyResponseDto getInbodyByUuid(UUID inbodyUuid) {
        Inbody inbody = inbodyRepository.findByInbodyUuidAndIsDeletedFalse(inbodyUuid);
        return inbodyMapper.toDto(inbody);
    }

    public InbodyResponseDto updateInbody(UUID inbodyUuid, InbodyRequestDto requestDto) {
        Inbody existingInbody = inbodyRepository.findByInbodyUuidAndIsDeletedFalse(inbodyUuid);
        if (existingInbody != null) {
            Inbody updatedInbody = inbodyMapper.toEntity(requestDto);
            Inbody savedInbody = inbodyRepository.save(updatedInbody);
            return inbodyMapper.toDto(savedInbody);
        }
        return null;
    }


    public boolean deleteInbody(UUID inbodyUuid) {
        Inbody existingInbody = inbodyRepository.findByInbodyUuidAndIsDeletedFalse(inbodyUuid);
        if (existingInbody != null) {
            existingInbody.setDeleted();
            inbodyRepository.save(existingInbody);
            return true;
        }
        return false;
    }

    public List<InbodyResponseDto> getAllInbodiesByMemberUuid(UUID memberUuid) {
        List<Inbody> inbodies = inbodyRepository.findAllByMemberUuidAndIsDeletedFalse(memberUuid);
        return inbodies.stream()
                .map(inbodyMapper::toDto)
                .collect(Collectors.toList());
    }
}
