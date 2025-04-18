package com.cogify.poll.application.dto;

import java.util.List;

public record PollRequestDto(String title,
                            String description,
                            List<OptionRequestDto> options) {}
