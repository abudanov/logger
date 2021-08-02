package io.github.abudanov.logger.service;

import io.github.abudanov.logger.dto.MessageDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {
    void saveMessages(List<MessageDto> messages, boolean force);

    List<MessageDto> getMessagesByQueueWithTime(String queue, LocalDateTime created_from,
                                                LocalDateTime created_until);

    MessageDto getMessageById(Long id);
}
