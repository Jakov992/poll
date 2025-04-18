package com.cogify.poll.application.mapper;

import com.cogify.poll.application.dto.*;
import com.cogify.poll.domain.Option;
import com.cogify.poll.domain.Poll;
import com.cogify.poll.domain.Vote;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PollDtoMapperShould {

    private final PollDtoMapper mapper = new PollDtoMapper();

    @Test
    void testToDto_fromPoll_withOptionsAndVotes() {
        Long voteId = 1L;
        String userId = "userId";
        Long optionId = 1L;
        Long pollId = 1L;

        Vote vote = Vote.builder().id(voteId).userId(userId).build();
        Option option = Option.builder().id(optionId).text("Option 1").votes(List.of(vote)).build();
        Poll poll = Poll.builder()
                .id(pollId)
                .title("Test Poll")
                .description("Test Description")
                .options(List.of(option))
                .build();

        PollResponseDto dto = mapper.toDto(poll);

        assertThat(dto.id()).isEqualTo(pollId);
        assertThat(dto.title()).isEqualTo("Test Poll");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.options()).hasSize(1);
        assertThat(dto.options().get(0).id()).isEqualTo(optionId);
        assertThat(dto.options().get(0).text()).isEqualTo("Option 1");
        assertThat(dto.options().get(0).votes()).hasSize(1);
        assertThat(dto.options().get(0).votes().get(0).id()).isEqualTo(voteId);
        assertThat(dto.options().get(0).votes().get(0).userId()).isEqualTo(userId);
    }

    @Test
    void testToDto_fromOption_withoutVotes() {
        Long optionId = 1L;
        Option option = Option.builder().id(optionId).text("Alone Option").votes(null).build();

        OptionResponseDto dto = mapper.toDto(option);

        assertThat(dto.id()).isEqualTo(optionId);
        assertThat(dto.text()).isEqualTo("Alone Option");
        assertThat(dto.votes()).isEmpty();
    }

    @Test
    void testToDto_fromVote() {
        Long voteId = 1L;
        String userId = "userId";
        Vote vote = Vote.builder().id(voteId).userId(userId).build();

        VoteResponseDto dto = mapper.toDto(vote);

        assertThat(dto.id()).isEqualTo(voteId);
        assertThat(dto.userId()).isEqualTo(userId);
    }

    @Test
    void testToDomain_fromPollRequestDto_withOptions() {
        OptionRequestDto optionDto = new OptionRequestDto("Yes");
        PollRequestDto pollDto = new PollRequestDto("New Poll", "Some Description", List.of(optionDto));

        Poll poll = mapper.toDomain(pollDto);

        assertThat(poll.getTitle()).isEqualTo("New Poll");
        assertThat(poll.getDescription()).isEqualTo("Some Description");
        assertThat(poll.getOptions()).hasSize(1);
        assertThat(poll.getOptions().get(0).getText()).isEqualTo("Yes");
    }

    @Test
    void testToDomain_fromOptionRequestDto() {
        OptionRequestDto optionDto = new OptionRequestDto("Option Text");
        Option option = mapper.toDomain(optionDto);

        assertThat(option.getText()).isEqualTo("Option Text");
    }
}