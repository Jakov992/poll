package com.jakov.vjezba2.domain;

public interface VoteService {
    
    Poll vote(Long pollId, Long optionId, Long userId);
}
