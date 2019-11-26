package messaging;

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
    private Destination redirectDestination;
    private Connection connection;
    private MessageConsumer consumer;
    private MessageProducer producer;

    private Map<String, TextMessage> sentMessages= new HashMap<String, TextMessage>();
    private List<TextMessage> receivedMessages = new ArrayList<TextMessage>();

    private Gson gson = new Gson();

    public void setOnMessageReceieved(MessageRecieved function) {
        this.onMessageReceieved = function;
    }

    public void setOnMessageListUpdated(MessageListUpdated function) {
        this.onMessageListUpdated = function;
    }

    private MessageRecieved onMessageReceieved = null;
    private MessageListUpdated onMessageListUpdated = null;

    public Messager(String queue) {
        this(queue, null);
    }

    public Messager(String queue, String redirectQueue) {
        try {
            connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);;
            receiveDestination = session.createQueue(queue);
            if(redirectQueue != null) redirectDestination = session.createQueue(redirectQueue);

            consumer = session.createConsumer(receiveDestination);
            producer = session.createProducer(null);

        } catch (JMSException e) {
            System.out.println("MQ not running in tcp://localhost:61616");
            e.printStackTrace();
        }

        try {
            consumer.setMessageListener(msg -> {
                receivedMessages.add((TextMessage) msg);
                if(onMessageListUpdated != null) onMessageListUpdated.onMessageListUpdate();
                if (redirectQueue != null) {
                    redirect((TextMessage) msg);
                }
                if(onMessageReceieved != null) onMessageReceieved.onMessage((TextMessage) msg);
            });

            connection.start();

        } catch (JMSException e) {
            System.out.println("Error creating message listener");
            e.printStackTrace();
        }
    }

    public void redirect(TextMessage msg) {
        try {
            msg.setJMSDestination(receiveDestination);

            producer.send(receiveDestination, msg);

            sentMessages.put(msg.getJMSMessageID(), msg);

            if(onMessageListUpdated != null) onMessageListUpdated.onMessageListUpdate();

        } catch (JMSException e) {
            System.out.println("Error redirecting message: " + msg);
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

            if(onMessageListUpdated != null) onMessageListUpdated.onMessageListUpdate();

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

            if(onMessageListUpdated != null) onMessageListUpdated.onMessageListUpdate();

        } catch (JMSException e) {
            System.out.println("Error sending message: " + message);
            e.printStackTrace();
        }
    }


    public List<TextMessage> getReceivedMessages() { return receivedMessages; }
    public List<TextMessage> getSentMessages() { return (List<TextMessage>) sentMessages.values(); }

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

    public TextMessage getSentMessage(String id) {
        return sentMessages.get(id);
    }
}