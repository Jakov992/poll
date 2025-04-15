package com.jakov.vjezba2.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Poll {
    private final Long id;
    private final String title;
    private final String description;
    private List<Option> options;
}
