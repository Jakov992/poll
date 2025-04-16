package com.cogify.poll.infrastructure.mapper;

import com.cogify.poll.domain.Option;
import com.cogify.poll.domain.Poll;
import com.cogify.poll.domain.Vote;
import com.cogify.poll.infrastructure.OptionEntity;
import com.cogify.poll.infrastructure.PollEntity;
import com.cogify.poll.infrastructure.VoteEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PollEntityMapperShould {

    private final PollEntityMapper mapper = new PollEntityMapper();

    @Test
    void map_poll_entity_to_domain_poll() {
        VoteEntity voteEntity = VoteEntity.builder()
                .id(1L)
                .userId(10L)
                .build();

        OptionEntity optionEntity = OptionEntity.builder()
                .id(2L)
                .text("Option A")
                .votes(List.of(voteEntity))
                .build();

        PollEntity pollEntity = PollEntity.builder()
                .id(100L)
                .title("Test Poll")
                .description("Description")
                .options(List.of(optionEntity))
                .build();

        voteEntity.setOption(optionEntity);
        optionEntity.setPoll(pollEntity); // link back

        // Act
        Poll domain = mapper.toDomain(pollEntity);

        // Assert
        assertThat(domain.getId()).isEqualTo(100L);
        assertThat(domain.getTitle()).isEqualTo("Test Poll");
        assertThat(domain.getOptions()).hasSize(1);

        Option option = domain.getOptions().get(0);
        assertThat(option.getText()).isEqualTo("Option A");
        assertThat(option.getVotes()).hasSize(1);
        assertThat(option.getVotes().get(0).getUserId()).isEqualTo(10L);
    }

    @Test
    void map_domain_poll_to_poll_entity() {
        Vote vote = Vote.builder()
                .id(1L)
                .userId(10L)
                .optionId(2L)
                .build();

        Option option = Option.builder()
                .id(2L)
                .pollId(100L)
                .text("Option A")
                .votes(List.of(vote))
                .build();

        Poll poll = Poll.builder()
                .id(100L)
                .title("Test Poll")
                .description("Description")
                .options(List.of(option))
                .build();

        // Act
        PollEntity entity = mapper.toEntity(poll);

        // Assert
        assertThat(entity.getTitle()).isEqualTo("Test Poll");
        assertThat(entity.getOptions()).hasSize(1);

        OptionEntity opt = entity.getOptions().get(0);
        assertThat(opt.getText()).isEqualTo("Option A");
        assertThat(opt.getVotes()).hasSize(1);
        assertThat(opt.getVotes().get(0).getUserId()).isEqualTo(10L);
    }
}