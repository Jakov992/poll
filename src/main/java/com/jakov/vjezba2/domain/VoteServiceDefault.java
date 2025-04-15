package com.jakov.vjezba2.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VoteServiceDefault implements VoteService {
    
    private final PollRepository pollRepository;

    @Override
    public Poll vote(Long pollId, Long optionId, Long userId) {
        Poll poll = pollRepository.get(pollId).orElseThrow(RuntimeException::new);

        System.out.println(poll);
        // Remove the user's previous votes from all options
        for (Option option : poll.getOptions()) {
            List<Vote> filteredVotes = option.getVotes().stream()
                    .filter(vote -> vote.getUserId() != userId)
                    .toList();
            option.setVotes(filteredVotes);
        }

        // Add the new vote to the selected option
        for (Option option : poll.getOptions()) {
            if (Objects.equals(option.getId(), optionId)) {
                List<Vote> votes = new ArrayList<>(option.getVotes());
                votes.add(Vote.builder()
                        .userId(userId)
                        .optionId(optionId)
                        .build());
                option.setVotes(votes);
                break;
            }
        }

        System.out.println(poll);
        return pollRepository.save(poll);
    }
}
