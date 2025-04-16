package com.cogify.poll.application.dto;

import java.util.List;

public record PollDto(
        Long id,
        String title,
        String description,
        List<OptionDto> options
) {}
