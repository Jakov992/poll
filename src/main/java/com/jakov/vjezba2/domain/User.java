package com.jakov.vjezba2.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}