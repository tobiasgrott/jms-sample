package com.grott.globalTopic;

import java.util.concurrent.TimeUnit;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "topicWorker2", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/sampletopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class JmsTopicListener2 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            TimeUnit.SECONDS.sleep(30);
            System.out.println("Topic Worker 2: " + textMessage.getText());
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}