package io.github.abudanov.logger.repository;

import java.time.LocalDateTime;

public interface MessageView {
    Long getId();
    LocalDateTime getCreatedAt();
    String getQueue();
    String getBody();
}
