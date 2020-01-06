package com.grott.queue;

import java.util.concurrent.TimeUnit;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "queueWorker1", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/samplequeue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class JmsQueueListener1 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            TimeUnit.SECONDS.sleep(15);
            System.out.println("Queue Worker 1: " + textMessage.getText());
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}