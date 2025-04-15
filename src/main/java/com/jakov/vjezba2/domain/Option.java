package com.jakov.vjezba2.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Option {
    private final Long id;
    private final Long pollId;
    private final String text;
    private List<Vote> votes;
}
