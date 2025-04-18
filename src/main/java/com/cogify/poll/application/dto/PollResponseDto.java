package com.cogify.poll.application.dto;

import java.util.List;

public record PollResponseDto(
        Long id,
        String title,
        String description,
        List<OptionResponseDto> options
) {}
