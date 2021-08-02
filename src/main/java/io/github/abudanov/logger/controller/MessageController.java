package io.github.abudanov.logger.controller;

import io.github.abudanov.logger.dto.MessageDto;
import io.github.abudanov.logger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Validated
public class MessageController {

    @Autowired
    private final MessageService messageService;

    @GetMapping("/")
    public List<MessageDto> getAll(
            @Nullable String queue,
            @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime created_from,
            @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime created_until
    ) {
        return messageService.getMessagesByQueueWithTime(queue, created_from, created_until);
    }

    @GetMapping("/{id}")
    public MessageDto getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void logMessages(@RequestBody List<MessageDto> messages, @RequestParam boolean force) {
        messageService.saveMessages(messages, force);
    }

}
