package io.github.abudanov.logger.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageDto implements Serializable {
    private Long id;
    private String queue;
    private String body;
    private LocalDateTime createdAt = LocalDateTime.now();
}
