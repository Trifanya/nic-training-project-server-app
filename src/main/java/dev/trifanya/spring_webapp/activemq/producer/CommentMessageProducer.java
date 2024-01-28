package dev.trifanya.spring_webapp.activemq.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommentMessageProducer {
    private JmsTemplate jmsTemplate;

    public void sendGetCommentMessage() {
        String messageToSend = null;

        jmsTemplate.convertAndSend(messageToSend);
    }

    public void sendGetCommentListMessage() {
        String messageToSend = null;

        jmsTemplate.convertAndSend(messageToSend);
    }
}
