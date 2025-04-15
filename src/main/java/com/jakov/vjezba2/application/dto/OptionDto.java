package com.jakov.vjezba2.application.dto;

import java.util.List;

public record OptionDto(
        Long id,
        String text,
        List<VoteDto> votes
) {}
