package com.cogify.poll.domain;

import java.util.List;
import java.util.Optional;

public interface PollRepository {
    
    Poll save(Poll poll);
    
    Optional<Poll> get(Long id);
    
    List<Poll> getPolls();
}
