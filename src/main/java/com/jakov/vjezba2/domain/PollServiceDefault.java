package com.jakov.vjezba2.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollServiceDefault implements PollService {
    
    private final PollRepository pollRepository;

    @Override
    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public List<Poll> getPolls() {
        return pollRepository.getPolls();
    }
}
