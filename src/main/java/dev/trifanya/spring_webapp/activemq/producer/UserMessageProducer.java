package dev.trifanya.spring_webapp.activemq.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserMessageProducer {
    private JmsTemplate jmsTemplate;

    public void sendGetUserMessage() {
        String messageToSend = null;

        jmsTemplate.convertAndSend(messageToSend);
    }

    public void sendGetUserListMessage() {
        String messageToSend = null;

        jmsTemplate.convertAndSend(messageToSend);
    }
}
