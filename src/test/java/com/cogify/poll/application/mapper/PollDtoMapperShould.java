//package com.cogify.poll.application.mapper;
//
//import com.cogify.poll.application.dto.OptionResponseDto;
//import com.cogify.poll.application.dto.PollResponseDto;
//import com.cogify.poll.application.dto.VoteResponseDto;
//import com.cogify.poll.domain.Option;
//import com.cogify.poll.domain.Poll;
//import com.cogify.poll.domain.Vote;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class PollDtoMapperShould {
//
//    private final PollDtoMapper mapper = new PollDtoMapper();
//
//    @Test
//    void mapVoteToDtoAndBack() {
//        Vote vote = Vote.builder()
//                .id(1L)
//                .userId(42L)
//                .build();
//
//        VoteResponseDto dto = mapper.toDto(vote);
//        assertThat(dto.id()).isEqualTo(1L);
//        assertThat(dto.userId()).isEqualTo(42L);
//
//        Vote mappedBack = mapper.toDomain(dto);
//        assertThat(mappedBack).usingRecursiveComparison().isEqualTo(vote);
//    }
//
//    @Test
//    void mapOptionToDtoAndBack() {
//        Vote vote = Vote.builder().id(1L).userId(42L).build();
//        Option option = Option.builder()
//                .id(10L)
//                .text("Option A")
//                .votes(List.of(vote))
//                .build();
//
//        OptionResponseDto dto = mapper.toDto(option);
//        assertThat(dto.id()).isEqualTo(10L);
//        assertThat(dto.text()).isEqualTo("Option A");
//        assertThat(dto.votes()).hasSize(1);
//        assertThat(dto.votes().get(0).userId()).isEqualTo(42L);
//
//        Option mappedBack = mapper.toDomain(dto);
//        assertThat(mappedBack).usingRecursiveComparison().isEqualTo(option);
//    }
//
//    @Test
//    void mapPollToDtoAndBack() {
//        Vote vote = Vote.builder().id(1L).userId(42L).build();
//        Option option = Option.builder()
//                .id(10L)
//                .text("Option A")
//                .votes(List.of(vote))
//                .build();
//        Poll poll = Poll.builder()
//                .id(100L)
//                .title("My Poll")
//                .description("Pick one")
//                .options(List.of(option))
//                .build();
//
//        PollResponseDto dto = mapper.toDto(poll);
//        assertThat(dto.id()).isEqualTo(100L);
//        assertThat(dto.title()).isEqualTo("My Poll");
//        assertThat(dto.description()).isEqualTo("Pick one");
//        assertThat(dto.options()).hasSize(1);
//        assertThat(dto.options().get(0).text()).isEqualTo("Option A");
//
//        Poll mappedBack = mapper.toDomain(dto);
//        assertThat(mappedBack).usingRecursiveComparison().isEqualTo(poll);
//    }
//
//    @Test
//    void handleNullOptionsAndVotesGracefully() {
//        Poll poll = Poll.builder()
//                .id(1L)
//                .title("Empty Poll")
//                .description("No options")
//                .options(null)
//                .build();
//
//        PollResponseDto dto = mapper.toDto(poll);
//        assertThat(dto.options()).isEmpty();
//
//        Poll mappedBack = mapper.toDomain(dto);
//        assertThat(mappedBack.getOptions()).isEmpty();
//    }
//}