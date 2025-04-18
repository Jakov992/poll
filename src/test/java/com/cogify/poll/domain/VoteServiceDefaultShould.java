package com.cogify.poll.domain;

import com.cogify.poll.domain.exception.PollDoesntExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoteServiceDefaultShould {

    @Mock
    private PollRepository pollRepository;

    @InjectMocks
    private VoteServiceDefault voteService;

    @Test
    void replaceUserVoteWithNewVote() {
        // Given
        Long pollId = 1L;
        String userId = "sgrdfgf-462354-45634rfgsd";
        Long previousOptionId = 10L;
        Long selectedOptionId = 20L;

        Vote previousVote = Vote.builder().userId(userId).optionId(previousOptionId).build();

        Option previousOption = Option.builder()
                .id(previousOptionId)
                .votes(new ArrayList<>(List.of(previousVote)))
                .build();

        Option selectedOption = Option.builder()
                .id(selectedOptionId)
                .votes(new ArrayList<>())
                .build();

        Poll poll = Poll.builder()
                .id(pollId)
                .options(List.of(previousOption, selectedOption))
                .build();

        given(pollRepository.get(pollId)).willReturn(Optional.of(poll));
        given(pollRepository.save(any(Poll.class))).willAnswer(invocation -> invocation.getArgument(0));

        // When
        Poll updatedPoll = voteService.vote(pollId, selectedOptionId, userId);

        // Then
        assertThat(previousOption.getVotes()).noneMatch(v -> v.getUserId().equals(userId));
        assertThat(selectedOption.getVotes()).anyMatch(v ->
                v.getUserId().equals(userId) && v.getOptionId().equals(selectedOptionId)
        );
        verify(pollRepository).save(updatedPoll);
    }

    @Test
    void throwIfPollNotFound() {
        // Given
        Long pollId = 999L;
        given(pollRepository.get(pollId)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> voteService.vote(pollId, 1L, "not-found"))
                .isInstanceOf(PollDoesntExistException.class);
    }
}