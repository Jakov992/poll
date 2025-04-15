package com.jakov.vjezba2.application;

import com.jakov.vjezba2.application.dto.PollDto;
import com.jakov.vjezba2.application.dto.VoteRequestDto;
import com.jakov.vjezba2.application.mapper.PollDtoMapper;
import com.jakov.vjezba2.domain.Poll;
import com.jakov.vjezba2.domain.PollService;
import com.jakov.vjezba2.domain.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;
    private final VoteService voteService;
    private final PollDtoMapper pollDtoMapper;

    @PostMapping
    public ResponseEntity<PollDto> create(@RequestBody PollDto pollDto) {
        Poll poll = pollDtoMapper.toDomain(pollDto);
        Poll created = pollService.createPoll(poll);
        return ResponseEntity.ok(pollDtoMapper.toDto(created));
    }

    @GetMapping
    public ResponseEntity<List<PollDto>> getAll() {
        List<PollDto> dtoList = pollService.getPolls().stream()
                .map(pollDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/{pollId}/vote")
    public ResponseEntity<PollDto> voteOnPoll(
            @PathVariable Long pollId,
            @RequestBody VoteRequestDto request
    ) {
        Poll updatedPoll = voteService.vote(pollId, request.optionId(), request.userId());
        return ResponseEntity.ok(pollDtoMapper.toDto(updatedPoll));
    }
}
