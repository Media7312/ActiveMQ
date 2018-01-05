import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessConsumer {
    //连接配置
    private static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //创建工厂
        ConnectionFactory connectionFactory;
        //创建连接
        Connection connection;
        //创建session
        Session session;
        //消息目的地
        Destination destination;
        //创建消费者
        MessageConsumer messageConsumer;


        connectionFactory = new ActiveMQConnectionFactory(MessConsumer.USER_NAME,MessConsumer.USER_PASSWORD,MessConsumer.URL);

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue("HelloWorld");

            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if (textMessage != null){
                    System.out.println(textMessage);
                }else {
                    System.out.println("接收完毕！");
                    break;
                }

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
