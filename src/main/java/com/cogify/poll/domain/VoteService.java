package com.cogify.poll.domain;

public interface VoteService {
    
    Poll vote(Long pollId, Long optionId, String userId);
}
