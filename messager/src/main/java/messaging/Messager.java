package messaging;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messager<TypeReceived, TypeSent> {

    private Session session;
    private Destination destinationReceive;
    private Destination destinationSend;
    private String queueReceive = null;
    private String queueSend = null;
    private Type typeReceived;
    private Type typeSent;

    private Connection connection;
    private MessageConsumer consumer;
    private MessageProducer producer;

    private Map<String, TextMessage> sentMessages= new HashMap<String, TextMessage>();
    private Map<String, MessageReceived<TypeReceived>> onReplys= new HashMap<String, MessageReceived<TypeReceived>>();
    private List<TextMessage> receivedMessages = new ArrayList<TextMessage>();

    private Gson gson = new Gson();

    private MessageReceived<TypeReceived> onMessageReceived = null;

    public void setOnMessageReceived(MessageReceived<TypeReceived> function) { this.onMessageReceived = function; }

    public MessageReceived<TypeReceived> getOnMessageReceived() { return onMessageReceived; }

    public Messager(String queueSend, String queueReceive, Class<TypeReceived> typeReceived, Class<TypeSent> typeSent) {
        this.queueReceive = queueReceive;
        this.queueSend = queueSend;
        this.typeReceived = typeReceived;
        this.typeSent = typeSent;

        try {
            connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);;
            destinationReceive = session.createQueue(this.queueReceive);
            destinationSend = session.createQueue(this.queueSend);

            consumer = session.createConsumer(destinationReceive);
            producer = session.createProducer(null);

        } catch (JMSException e) {
            System.out.println("MQ not running in tcp://localhost:61616");
            e.printStackTrace();
        }

        try {
            if (consumer != null) {
                consumer.setMessageListener(msg -> {
                    try {
                        TypeReceived messageParsed = gson.fromJson(((TextMessage) msg).getText(), typeReceived);
                        receivedMessages.add((TextMessage) msg);
                        if(onMessageReceived != null) onMessageReceived.onMessage(messageParsed);


                        // TextMessage pairMessage = getReceivedMessage(msg.getJMSCorrelationID());
                        // TypeSent pairMessageParsed = gson.fromJson(pairMessage.getText(), typeSent);
                        MessageReceived<TypeReceived> onReply = onReplys.get(msg.getJMSCorrelationID());
                        if (onReply != null) {
                            onReply.onMessage(messageParsed);
                            onReplys.remove(msg.getJMSCorrelationID());
                        }

                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                });

                connection.start();
            }
        } catch (JMSException e) {
            System.out.println("Error creating message listener");
            e.printStackTrace();
        }
    }

    public void reply(TypeReceived original, TypeSent sent){
        String originalJson = gson.toJson(sent);
        for (TextMessage message : sentMessages.values()) {
            try {
                if(message.getText().equals(originalJson)) {
                    reply(message.getJMSMessageID(), sent);
                    return;
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void reply(String id, TypeSent sent) {
        try {
            TextMessage receivedMsg = getReceivedMessage(id);
            if(receivedMsg!=null) {
                TextMessage sentMsg = session.createTextMessage(gson.toJson(sent));

                sentMsg.setJMSCorrelationID(receivedMsg.getJMSMessageID());
                Destination returnAddress = receivedMsg.getJMSReplyTo();
                sentMsg.setJMSDestination(returnAddress);

                producer.send(returnAddress, sentMsg);

                sentMessages.put(receivedMsg.getJMSMessageID(), sentMsg);
            }
        } catch (JMSException e) {
            System.out.println("Error replying message " + id + " with: " + sent);
            e.printStackTrace();
        }
    }

    public void send(TypeSent message) { this.send(message, null); }
    public void send(TypeSent message, MessageReceived<TypeReceived> onReply) {
        try {
            TextMessage sentMsg = session.createTextMessage(gson.toJson(message, typeSent));
            sentMsg.setJMSDestination(destinationSend);
            producer.send(destinationSend, sentMsg);
            sentMessages.put(sentMsg.getJMSMessageID(), sentMsg);
            onReplys.put(sentMsg.getJMSMessageID(), onReply);
        } catch (JMSException e) {
            System.out.println("Error sending message: " + message);
            e.printStackTrace();
        }
    }

    private List<TextMessage> getReceivedMessages() { return receivedMessages; }
    private List<TextMessage> getSentMessages() { return (List<TextMessage>) sentMessages.values(); }

    private TextMessage getReceivedMessage(String id) {
        for (TextMessage msg : receivedMessages) {
            try {
                if(msg.getJMSMessageID().equals(id)) return msg;
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private TextMessage getSentMessage(String id) { return sentMessages.get(id); }

}