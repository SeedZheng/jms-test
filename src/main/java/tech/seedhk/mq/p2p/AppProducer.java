package tech.seedhk.mq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Seed on 2017/10/14.
 * 队列P2P模式下的消息队列
 */
public class AppProducer {

    public static final  String url="tcp://127.0.0.1:61616";
    public static final String queueName="queueName";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);

        Connection connection=factory.createConnection();

        connection.start();

        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Destination destination=session.createQueue(queueName);

        MessageProducer messageProducer = session.createProducer(destination);

        for(int i=0;i<100;i++){
            TextMessage tm=session.createTextMessage("test send message "+i);
            messageProducer.send(tm);
            System.out.println("发送消息："+tm.getText());
        }

        session.close();
        connection.close();

    }
}
