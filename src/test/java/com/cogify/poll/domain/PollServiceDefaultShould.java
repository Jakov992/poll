package com.cogify.poll.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PollServiceDefaultShould {

    @Mock
    private PollRepository pollRepository;

    @InjectMocks
    private PollServiceDefault pollService;

    @Test
    void createPoll() {
        // given
        Poll inputPoll = Poll.builder().title("Test poll").build();
        Poll savedPoll = Poll.builder().id(1L).title("Test poll").build();

        given(pollRepository.save(inputPoll)).willReturn(savedPoll);

        // when
        Poll result = pollService.createPoll(inputPoll);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test poll");
        verify(pollRepository).save(inputPoll);
    }

    @Test
    void returnListOfPolls() {
        // given
        List<Poll> polls = List.of(
                Poll.builder().id(1L).title("Poll 1").build(),
                Poll.builder().id(2L).title("Poll 2").build()
        );

        given(pollRepository.getPolls()).willReturn(polls);

        // when
        List<Poll> result = pollService.getPolls();

        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).extracting(Poll::getTitle).containsExactly("Poll 1", "Poll 2");
        verify(pollRepository).getPolls();
    }
}