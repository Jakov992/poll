package com.cogify.poll.application.dto;

import java.util.List;

public record OptionResponseDto(
        Long id,
        String text,
        List<VoteResponseDto> votes
) {}
