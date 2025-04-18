package com.cogify.poll.application.mapper;

import com.cogify.poll.application.dto.*;
import com.cogify.poll.domain.Option;
import com.cogify.poll.domain.Poll;
import com.cogify.poll.domain.Vote;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PollDtoMapper {

    public PollResponseDto toDto(Poll poll) {
        return new PollResponseDto(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getOptions() != null
                        ? poll.getOptions().stream().map(this::toDto).toList()
                        : List.of()
        );
    }

    public OptionResponseDto toDto(Option option) {
        return new OptionResponseDto(
                option.getId(),
                option.getText(),
                option.getVotes() != null
                        ? option.getVotes().stream().map(this::toDto).toList()
                        : List.of()
        );
    }

    public VoteResponseDto toDto(Vote vote) {
        return new VoteResponseDto(vote.getId(), vote.getUserId());
    }

    public Poll toDomain(PollRequestDto dto) {
        return Poll.builder()
                .title(dto.title())
                .description(dto.description())
                .options(dto.options() != null
                        ? dto.options().stream().map(this::toDomain).toList()
                        : List.of())
                .build();
    }

    public Option toDomain(OptionRequestDto dto) {
        return Option.builder()
                .text(dto.text())
                .build();
    }

    public Vote toDomain(VoteResponseDto dto) {
        return Vote.builder()
                .id(dto.id())
                .userId(dto.userId())
                .build();
    }
}
