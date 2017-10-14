package tech.seedhk.mq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Seed on 2017/10/14.
 *
 * 队列topic模式下的消息队列
 *
 * *消费者必须提前订阅生产者的消息，即消费者必须比生产者提前运行 才能收到生产者的消息
 * 每个消费者收到的消息都是相同的，类似广播
 *
 */
public class AppCusumer {


    public  static final String url="tcp://127.0.0.1:61616";
    public  static  final String topicName="topicName";

    public static void main(String[] args) throws JMSException {

        //创建factory
        ConnectionFactory factory=new ActiveMQConnectionFactory(url);

        //创建Connection
        Connection connection=factory.createConnection();

        //开启连接
        connection.start();

        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Destination destination=session.createTopic(topicName);

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
