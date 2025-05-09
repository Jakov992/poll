package com.cogify.poll.domain;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Vote {
    private final Long id;
    private final String userId;
    private Long optionId;
}
