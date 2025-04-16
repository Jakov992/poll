package com.cogify.poll.infrastructure;

import com.cogify.poll.domain.Poll;
import com.cogify.poll.domain.PollRepository;
import com.cogify.poll.infrastructure.mapper.PollEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PollRepositoryDefault implements PollRepository {
    
    private final PollJpaRepository pollJpaRepository;
    private final PollEntityMapper pollEntityMapper;
    
    @Override
    public Poll save(Poll poll) {
        return pollEntityMapper.toDomain(pollJpaRepository.save(pollEntityMapper.toEntity(poll)));
    }

    @Override
    public Optional<Poll> get(Long id) {
        return Optional.of(pollEntityMapper.toDomain(Objects.requireNonNull(pollJpaRepository.findById(id).orElse(null))));
    }

    @Override
    public List<Poll> getPolls() {
        return pollJpaRepository.findAll().stream()
                .map(pollEntityMapper::toDomain)
                .toList();    
    }
}
