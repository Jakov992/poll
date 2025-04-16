package com.cogify.poll.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PollJpaRepository extends JpaRepository<PollEntity, Long> {
}
