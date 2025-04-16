package com.cogify.poll.domain;

import com.cogify.poll.domain.exception.PollDoesntExistException;
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
        Poll poll = pollRepository.get(pollId).orElseThrow(PollDoesntExistException::new);

        // Remove the user's previous votes from all options
        for (Option option : poll.getOptions()) {
            List<Vote> filteredVotes = option.getVotes().stream()
                    .filter(vote -> !Objects.equals(vote.getUserId(), userId))
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

        return pollRepository.save(poll);
    }
}
