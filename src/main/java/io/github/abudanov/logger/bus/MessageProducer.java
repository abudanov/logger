package io.github.abudanov.logger.bus;

import io.github.abudanov.logger.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageProducer {

    @Autowired
    private final JmsTemplate jmsTemplate;

    public void send(MessageDto messageDto) {
        jmsTemplate.send(messageDto.getQueue(), session -> session.createObjectMessage(messageDto));
    }

    public void sendAll(List<MessageDto> messageDtoList) {
        for (MessageDto messageDto: messageDtoList) {
            send(messageDto);
        }
    }


}
