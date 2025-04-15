package com.jakov.vjezba2.application.mapper;

import com.jakov.vjezba2.application.dto.OptionDto;
import com.jakov.vjezba2.application.dto.PollDto;
import com.jakov.vjezba2.application.dto.VoteDto;
import com.jakov.vjezba2.domain.Option;
import com.jakov.vjezba2.domain.Poll;
import com.jakov.vjezba2.domain.Vote;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PollDtoMapper {

    public PollDto toDto(Poll poll) {
        return new PollDto(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getOptions() != null
                        ? poll.getOptions().stream().map(this::toDto).toList()
                        : List.of()
        );
    }

    public OptionDto toDto(Option option) {
        return new OptionDto(
                option.getId(),
                option.getText(),
                option.getVotes() != null
                        ? option.getVotes().stream().map(this::toDto).toList()
                        : List.of()
        );
    }

    public VoteDto toDto(Vote vote) {
        return new VoteDto(vote.getId(), vote.getUserId());
    }

    public Poll toDomain(PollDto dto) {
        return Poll.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .options(dto.options() != null
                        ? dto.options().stream().map(this::toDomain).toList()
                        : List.of())
                .build();
    }

    public Option toDomain(OptionDto dto) {
        return Option.builder()
                .id(dto.id())
                .text(dto.text())
                .votes(dto.votes() != null
                        ? dto.votes().stream().map(this::toDomain).toList()
                        : List.of())
                .build();
    }

    public Vote toDomain(VoteDto dto) {
        return Vote.builder()
                .id(dto.id())
                .userId(dto.userId())
                .build();
    }
}
