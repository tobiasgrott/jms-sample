package com.grott.globalTopic;

import java.time.Instant;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.json.Json;
import javax.json.JsonObject;

@Singleton
public class JmsTopicProducer {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory jmsFactory;

    @Resource(lookup = "jms/sampletopic")
    private Topic sampleTopic;

    private int messageNumber = 0;

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void sendMessage() {
        TextMessage message;
        messageNumber++;
        try(Connection connection = jmsFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(sampleTopic)){
            JsonObject payload = Json.createObjectBuilder() //
                .add("messageNumber", messageNumber) //
                .add("timestamp", Instant.now().toEpochMilli()).build();

            message = session.createTextMessage(payload.toString());
            producer.send(message);
            System.out.println("Topic message produced: "+payload.toString());
        }catch (JMSException e){
            e.printStackTrace();
        }
    }
}
