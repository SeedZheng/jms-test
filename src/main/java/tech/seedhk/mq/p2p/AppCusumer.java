package tech.seedhk.mq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Seed on 2017/10/14.
 *
 * 队列P2P模式下的消息队列
 *
 * *消费者可以存在多个，在存在多个消费者的情况下，这些消费者会平均消费掉所有消息
 *
 */
public class AppCusumer {


    public  static final String url="tcp://127.0.0.1:61616";
    public  static  final String queueName="queueName";

    public static void main(String[] args) throws JMSException {

        //创建factory
        ConnectionFactory factory=new ActiveMQConnectionFactory(url);

        //创建Connection
        Connection connection=factory.createConnection();

        //开启连接
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
