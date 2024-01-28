package dev.trifanya.spring_webapp.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class TaskMessageConsumer {
    @JmsListener(destination = "Response Queue", containerFactory = "jmsFactory")
    public void receiveMessage(TextMessage textMessage) throws JMSException {
        System.out.println(textMessage.getText());
    }
}
