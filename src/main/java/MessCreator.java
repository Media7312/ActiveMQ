import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessCreator {
    //账号密码
    private static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD = ActiveMQConnection.DEFAULT_USER;
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int MESSAGE_NUM=10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer messageProducer;

//        实例化一个工厂
        connectionFactory = new ActiveMQConnectionFactory(MessCreator.USER_NAME, MessCreator.USER_PASSWORD, MessCreator.URL);

        try {
//        通过工厂创立连接
            connection = connectionFactory.createConnection();
            connection.start();
            ;

//        创建session
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);

//        创建消息队列
            destination = session.createQueue("HelloWorld");
//        消息生产者
            messageProducer = session.createProducer(destination);

//        发送消息、
            sendMessage(session,messageProducer);

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
    public static void sendMessage (Session session,MessageProducer messageProducer) throws JMSException{
        for (int i = 0; i < MessCreator.MESSAGE_NUM; i ++){
            //创建文本消息
            TextMessage textMessage = session.createTextMessage("这是一条文本消息+++++++" + i);
            System.out.println("消息队列" + i);

            messageProducer.send(textMessage);
        }
    }
}
