package loanclient.model;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messager {

    private Session session;
    private Destination receiveDestination;
    private Connection connection;
    private MessageConsumer consumer;
    private MessageProducer producer;

    private Map<String, TextMessage> sentMessages= new HashMap<String, TextMessage>();
    private List<TextMessage> receivedMessages = new ArrayList<TextMessage>();

    private Runnable onMessageChange;

    private Gson gson = new Gson();

    public Messager(String queue, Runnable onMessageChange) {
        this.onMessageChange = onMessageChange;

        try {
            connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);;
            receiveDestination = session.createQueue(queue);

            consumer = session.createConsumer(receiveDestination);
            producer = session.createProducer(null);

        } catch (JMSException e) {
            System.out.println("MQ not running in tcp://localhost:61616");
            e.printStackTrace();
        }

        try {
            consumer.setMessageListener(msg -> {
                receivedMessages.add((TextMessage) msg);
                onMessageChange.run();
            });

            connection.start();

        } catch (JMSException e) {
            System.out.println("Error creating message listener");
            e.printStackTrace();
        }
    }

    public void reply(String id, Object message) {
        try {
            TextMessage receivedMsg = getReceivedMessage(id);
            TextMessage sentMsg = session.createTextMessage(gson.toJson(message));

            sentMsg.setJMSCorrelationID(receivedMsg.getJMSMessageID());
            Destination returnAddress = receivedMsg.getJMSReplyTo();
            sentMsg.setJMSDestination(returnAddress);

            producer.send(returnAddress, sentMsg);

            sentMessages.put(receivedMsg.getJMSMessageID(), sentMsg);

            onMessageChange.run();

        } catch (JMSException e) {
            System.out.println("Error replying message " + id + " with: " + message);
            e.printStackTrace();
        }
    }

    public void send(Object message) {
        try {
            TextMessage sentMsg = session.createTextMessage(gson.toJson(message));

            sentMsg.setJMSDestination(receiveDestination);

            producer.send(receiveDestination, sentMsg);

            sentMessages.put(sentMsg.getJMSMessageID(), sentMsg);

            onMessageChange.run();

        } catch (JMSException e) {
            System.out.println("Error sending message: " + message);
            e.printStackTrace();
        }
    }


    public List<TextMessage> getReceivedMessages() { return receivedMessages; }
    public Map<String, TextMessage> getSentMessages() { return sentMessages; }

    public TextMessage getReceivedMessage(String id) {
        for (TextMessage msg : receivedMessages) {
            try {
                if(msg.getJMSMessageID().equals(id)) return msg;
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}