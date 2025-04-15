package com.jakov.vjezba2.application.dto;

public record VoteRequestDto(
    Long optionId,
    Long userId
) {}