package com.cogify.poll.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cogify.poll.application.dto.OptionDto;
import com.cogify.poll.application.dto.PollDto;
import com.cogify.poll.application.dto.VoteRequestDto;
import com.cogify.poll.application.mapper.PollDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class PollControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PollDtoMapper pollDtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Sql(value = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void createPoll() throws Exception {
        PollDto pollDto = new PollDto(
                null,
                "Favorite language?",
                "Choose one",
                List.of(
                        new OptionDto(null, "Java", List.of()),
                        new OptionDto(null, "Kotlin", List.of())
                )
        );

        mockMvc.perform(post("/api/polls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pollDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Favorite language?"))
                .andExpect(jsonPath("$.options").isArray())
                .andExpect(jsonPath("$.options.length()").value(2));
    }

    @Sql(value = "/sql/setupPollWithTwoOptions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void returnAllPolls() throws Exception {
        mockMvc.perform(get("/api/polls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Sql(value = "/sql/setupPollWithTwoOptions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void voteOnPoll() throws Exception {
        VoteRequestDto voteRequest = new VoteRequestDto(1L, 42L);

        mockMvc.perform(post("/api/polls/" + 1 + "/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Which language do you prefer?"))
                .andExpect(jsonPath("$.options").isArray())
                .andExpect(jsonPath("$.options.length()").value(2));
    }
}