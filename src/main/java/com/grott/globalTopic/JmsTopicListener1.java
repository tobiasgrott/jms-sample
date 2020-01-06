package com.grott.globalTopic;

import java.util.concurrent.TimeUnit;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "topicWorker1", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/sampletopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class JmsTopicListener1 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            TimeUnit.SECONDS.sleep(25);
            System.out.println("Topic worker 1: " + textMessage.getText());
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}