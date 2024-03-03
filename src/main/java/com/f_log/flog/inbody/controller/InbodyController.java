package com.f_log.flog.inbody.controller;

import com.f_log.flog.inbody.dto.InbodyRequestDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbody.service.InbodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inbody")
public class InbodyController {

    private final InbodyService inbodyService;

    @Autowired
    public InbodyController(InbodyService inbodyService) {
        this.inbodyService = inbodyService;
    }

    @PostMapping("/new")
    public ResponseEntity<InbodyResponseDto> createInbody(@RequestBody InbodyRequestDto requestDto) {
        InbodyResponseDto createdInbody = inbodyService.createInbody(requestDto);
        return new ResponseEntity<>(createdInbody, HttpStatus.CREATED);
    }

    @GetMapping("/{inbodyUuid}")
    public ResponseEntity<InbodyResponseDto> getInbodyByUuid(@PathVariable UUID inbodyUuid) {
        InbodyResponseDto inbodyResponseDto = inbodyService.getInbodyByUuid(inbodyUuid);
        if (inbodyResponseDto != null) {
            return new ResponseEntity<>(inbodyResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{inbodyUuid}")
    public ResponseEntity<InbodyResponseDto> updateInbody(@PathVariable UUID inbodyUuid, @RequestBody InbodyRequestDto requestDto) {
        InbodyResponseDto updatedInbody = inbodyService.updateInbody(inbodyUuid, requestDto);
        if (updatedInbody != null) {
            return new ResponseEntity<>(updatedInbody, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{inbodyUuid}")
    public ResponseEntity<Void> deleteInbody(@PathVariable UUID inbodyUuid) {
        boolean deleted = inbodyService.deleteInbody(inbodyUuid);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/{memberUuid}")
    public ResponseEntity<List<InbodyResponseDto>> getAllInbodiesByMemberUuid(@PathVariable UUID memberUuid) {
        List<InbodyResponseDto> inbodyResponseDtos = inbodyService.getAllInbodiesByMemberUuid(memberUuid);
        if (!inbodyResponseDtos.isEmpty()) {
            return new ResponseEntity<>(inbodyResponseDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/latest/{memberUuid}")
    public ResponseEntity<InbodyResponseDto> getLatestInbodyByMemberUuid(@PathVariable UUID memberUuid) {
        InbodyResponseDto found = inbodyService.getLatestInbodyOfMember(memberUuid);
        if (found != null) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
