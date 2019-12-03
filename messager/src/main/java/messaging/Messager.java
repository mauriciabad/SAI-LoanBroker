package messaging;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messager {

    private Session session;
    private Destination destinationFrom;
    private Destination destinationTo;
    private String queueFrom = null;
    private String queueTo = null;
    private Type typeFrom = null;
    private Type typeTo = null;
    private Connection connection;
    private MessageConsumer consumer;
    private MessageProducer producer;

    private Map<String, TextMessage> sentMessages= new HashMap<String, TextMessage>();
    private List<TextMessage> receivedMessages = new ArrayList<TextMessage>();

    private Gson gson = new Gson();

    private MessageReceived onMessageReceieved = null;
    private MessageListUpdated onMessageListUpdated = null;
    private ParseTypes parseTypes = null;

    public void setOnMessageReceived(MessageReceived function) { this.onMessageReceieved = function; }
    public void setOnMessageListUpdated(MessageListUpdated function) { this.onMessageListUpdated = function; }
    public void enableAutoRedirect(ParseTypes function) { this.parseTypes = function; }
    public void disableAutoRedirect() { this.parseTypes = null; }

    public Messager(String queueFrom, Type typeFrom, String queueTo, Type typeTo) {
        this.queueFrom = queueFrom;
        this.queueTo = queueTo;
        this.typeFrom = typeFrom;
        this.typeTo = typeTo;

        try {
            connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);;
            destinationFrom = session.createQueue(queueFrom);
            destinationTo = session.createQueue(queueTo);

            consumer = session.createConsumer(destinationFrom);
            producer = session.createProducer(null);

        } catch (JMSException e) {
            System.out.println("MQ not running in tcp://localhost:61616");
            e.printStackTrace();
        }

        try {
            consumer.setMessageListener(msg -> {
                try {
                    Object message = gson.fromJson(((TextMessage) msg).getText(), typeFrom);

                    receivedMessages.add((TextMessage) msg);

                    if(onMessageListUpdated != null) onMessageListUpdated.onMessageListUpdate();
                    if(onMessageReceieved != null)   onMessageReceieved.onMessage(message);
                    if(parseTypes != null)           send(parseTypes.parse(message));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

            connection.start();

        } catch (JMSException e) {
            System.out.println("Error creating message listener");
            e.printStackTrace();
        }
    }

    /*
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
    */

    public void send(Object message) {
        try {
            TextMessage sentMsg = session.createTextMessage(gson.toJson(message, typeTo));
            sentMsg.setJMSDestination(destinationTo);
            producer.send(destinationTo, sentMsg);
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

    public TextMessage getSentMessage(String id) { return sentMessages.get(id); }

}