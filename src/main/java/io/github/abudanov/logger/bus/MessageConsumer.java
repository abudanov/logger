package io.github.abudanov.logger.bus;

import io.github.abudanov.logger.dto.MessageDto;
import io.github.abudanov.logger.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageConsumer implements MessageListener {

    @Autowired
    private final MessageService messageService;


    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        MessageDto dto = message.getBody(MessageDto.class);
        messageService.saveMessages(List.of(dto), true);
    }
}
