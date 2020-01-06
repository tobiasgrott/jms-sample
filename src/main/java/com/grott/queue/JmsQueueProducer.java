package com.grott.queue;

import java.time.Instant;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;

@Singleton
public class JmsQueueProducer {

    private int messageNumber = 0;

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory jmsFactory;

    @Resource(lookup = "jms/samplequeue")
    private Queue queue;

    @Schedule(second = "*/15", minute = "*", hour = "*", persistent = false)
    public void sendMessage() {
        TextMessage message;
        messageNumber++;
        try(Connection connection = jmsFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue)){
            JsonObject payload = Json.createObjectBuilder() //
                .add("messageNumber", messageNumber) //
                .add("timestamp", Instant.now().toEpochMilli()).build();

            message = session.createTextMessage(payload.toString());
            producer.send(message);
            System.out.println("Queue sent: "+payload.toString());
        }catch (JMSException e){
            e.printStackTrace();
        }
    }
}
