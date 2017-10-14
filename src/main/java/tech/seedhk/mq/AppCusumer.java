package tech.seedhk.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Seed on 2017/10/14.
 */
public class AppCusumer {


    public  static final String url="tcp://127.0.0.1:61616";
    public  static  final String queueName="queueName";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory=new ActiveMQConnectionFactory(url);

        Connection connection=factory.createConnection();

        connection.start();

        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Destination destination=session.createQueue(queueName);

        MessageConsumer messageConsumer=session.createConsumer(destination);

        //消息接收是异步的，并不是同步的
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //session.close();
        //connection.close();


    }


}
