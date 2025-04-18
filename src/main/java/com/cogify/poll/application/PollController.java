package com.cogify.poll.application;

import com.cogify.poll.application.dto.PollRequestDto;
import com.cogify.poll.application.dto.PollResponseDto;
import com.cogify.poll.application.dto.VoteRequestDto;
import com.cogify.poll.application.mapper.PollDtoMapper;
import com.cogify.poll.domain.Poll;
import com.cogify.poll.domain.PollService;
import com.cogify.poll.domain.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<PollResponseDto> create(@RequestBody PollRequestDto pollDto) {
        Poll poll = pollDtoMapper.toDomain(pollDto);
        Poll created = pollService.createPoll(poll);
        return ResponseEntity.ok(pollDtoMapper.toDto(created));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PollResponseDto>> getAll() {
        List<PollResponseDto> dtoList = pollService.getPolls().stream()
                .map(pollDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/{pollId}/vote")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PollResponseDto> voteOnPoll(
            @PathVariable Long pollId,
            @RequestBody VoteRequestDto request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();

        Poll updatedPoll = voteService.vote(pollId, request.optionId(), userId);
        return ResponseEntity.ok(pollDtoMapper.toDto(updatedPoll));
    }
}
