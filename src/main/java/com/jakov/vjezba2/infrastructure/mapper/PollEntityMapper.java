package com.jakov.vjezba2.infrastructure.mapper;

import com.jakov.vjezba2.domain.Option;
import com.jakov.vjezba2.domain.Poll;
import com.jakov.vjezba2.domain.Vote;
import com.jakov.vjezba2.infrastructure.OptionEntity;
import com.jakov.vjezba2.infrastructure.PollEntity;
import com.jakov.vjezba2.infrastructure.VoteEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PollEntityMapper {

    public Poll toDomain(PollEntity entity) {
        return Poll.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .options(entity.getOptions() != null
                        ? entity.getOptions().stream().map(this::toDomain).toList()
                        : new ArrayList<>())
                .build();
    }

    private Option toDomain(OptionEntity entity) {
        return Option.builder()
                .id(entity.getId())
                .pollId(entity.getPoll().getId())
                .text(entity.getText())
                .votes(entity.getVotes() != null
                        ? entity.getVotes().stream().map(this::toDomain).toList()
                        : new ArrayList<>())
                .build();
    }

    private Vote toDomain(VoteEntity entity) {
        return Vote.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .optionId(entity.getOption().getId())
                .build();
    }

    public PollEntity toEntity(Poll domain) {
        PollEntity entity = new PollEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());

        List<OptionEntity> optionEntities = domain.getOptions() != null
                ? domain.getOptions().stream()
                .map(option -> toEntity(option, entity))
                .toList()
                : new ArrayList<>();

        entity.setOptions(optionEntities);
        return entity;
    }

    private OptionEntity toEntity(Option domain, PollEntity pollEntity) {
        OptionEntity entity = new OptionEntity();
        entity.setId(domain.getId());
        entity.setText(domain.getText());
        entity.setPoll(pollEntity);

        List<VoteEntity> voteEntities = domain.getVotes() != null
                ? domain.getVotes().stream()
                .map(vote -> toEntity(vote, entity))
                .toList()
                : new ArrayList<>();

        entity.setVotes(voteEntities);
        return entity;
    }

    private VoteEntity toEntity(Vote domain, OptionEntity optionEntity) {
        VoteEntity entity = new VoteEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setOption(optionEntity);
        return entity;
    }
}
