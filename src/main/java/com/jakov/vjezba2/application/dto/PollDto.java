package com.jakov.vjezba2.application.dto;

import java.util.List;

public record PollDto(
        Long id,
        String title,
        String description,
        List<OptionDto> options
) {}
