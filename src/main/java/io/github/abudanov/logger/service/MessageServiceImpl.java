package io.github.abudanov.logger.service;

import io.github.abudanov.logger.bus.MessageProducer;
import io.github.abudanov.logger.dto.MessageDto;
import io.github.abudanov.logger.entity.Message;
import io.github.abudanov.logger.repository.MessageRepository;
import io.github.abudanov.logger.repository.MessageView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final MessageProducer messageProducer;

    @Override
    public void saveMessages(List<MessageDto> messages, boolean force) {
        if(force) {
            persist(messages);
        } else {
            messageProducer.sendAll(messages);
        }
    }

    private void persist(List<MessageDto> messages) {
        List<Message> messageList = messages.stream().map(this::asEntities).collect(Collectors.toList());
        messageRepository.saveAll(messageList);
    }

    @Override
    public List<MessageDto> getMessagesByQueueWithTime(
            String queue,
            LocalDateTime created_from,
            LocalDateTime created_until
    ) {
        List<MessageView> messagesByQueueWithTime = messageRepository.getMessagesByQueueWithTime(queue, created_from, created_until);
        return messagesByQueueWithTime.stream().map(this::asResponse).collect(Collectors.toList());
    }

    @Override
    public MessageDto getMessageById(Long id) {
        return asResponse(messageRepository.getById(id));
    }

    private Message asEntities(MessageDto dto) {
        Message entity = new Message();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setQueue(dto.getQueue());
        entity.setBody(dto.getBody());
        return entity;
    }

    private MessageDto asResponse(Message entity) {
        MessageDto dto = new MessageDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setQueue(entity.getQueue());
        dto.setBody(entity.getBody());
        return dto;
    }

    private MessageDto asResponse(MessageView entity) {
        MessageDto dto = new MessageDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setQueue(entity.getQueue());
        dto.setBody(entity.getBody());
        return dto;
    }
}
