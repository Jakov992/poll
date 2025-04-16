package com.cogify.poll.domain;

import java.util.List;

public interface PollService {
    
    Poll createPoll(Poll poll);
    
    List<Poll> getPolls();
}
