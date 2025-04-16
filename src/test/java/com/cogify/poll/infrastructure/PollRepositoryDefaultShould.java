package com.cogify.poll.infrastructure;

import com.cogify.poll.domain.Option;
import com.cogify.poll.domain.Poll;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PollRepositoryDefaultShould {

    @Autowired
    private PollRepositoryDefault pollRepository;

    @Transactional
    @Sql(scripts = "/sql/setupPollWithTwoOptions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getPollById() {
        Optional<Poll> poll = pollRepository.get(1L);
        assertThat(poll).isPresent();
        assertThat(poll.get().getTitle()).isEqualTo("Which language do you prefer?");
        assertThat(poll.get().getOptions()).hasSize(2);
    }

    @Transactional
    @Sql(scripts = "/sql/setupPollWithTwoOptions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getAllPolls() {
        List<Poll> polls = pollRepository.getPolls();
        assertThat(polls).hasSize(1);
        assertThat(polls.get(0).getOptions()).hasSize(2);
    }

    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void savePoll() {
        Poll newPoll = new Poll(
                null,
                "New Poll",
                "Choose wisely",
                List.of(Option.builder().text("Yes").build(), Option.builder().text("No").build())
        );

        Poll saved = pollRepository.save(newPoll);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOptions()).hasSize(2);
    }
}