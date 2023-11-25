package com.withsports.recordservice.domain.record.matching.web;

import com.withsports.recordservice.domain.record.matching.service.MatchingRecordService;
import com.withsports.recordservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchingRecordController {

    private final MatchingRecordService teamRecordService;




}
