package com.cogify.poll.application.dto;

public record VoteRequestDto(
    Long optionId,
    Long userId
) {}