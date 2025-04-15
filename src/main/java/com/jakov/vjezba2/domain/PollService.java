package com.jakov.vjezba2.domain;

import java.util.List;

public interface PollService {
    
    Poll createPoll(Poll poll);
    
    List<Poll> getPolls();
}
