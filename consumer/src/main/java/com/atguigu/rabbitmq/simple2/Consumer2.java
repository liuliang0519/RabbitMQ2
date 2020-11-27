package com.atguigu.rabbitmq.simple2;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = Consumer2.getConnection();

        //创建信道
        Channel channel = connection.createChannel();

        //声明队列
        //如果有 该名称的队列就使用， 如果没有就创建
        channel.queueDeclare("simple_queue2", true, false, false, null
        );

       //接受消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /*
             回调方法,当收到消息后,会自动执行该方法
             1. consumerTag：标识
             2. envelope：获取一些信息,交换机,路由key...
             3. properties：配置信息
             4. body：数据
          */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag："+consumerTag);
                System.out.println("Exchange："+envelope.getExchange());
                System.out.println("RoutingKey："+envelope.getRoutingKey());
                System.out.println("properties："+properties);
                System.out.println("body："+new String(body));
            }
        };

         /*
            basicConsume(String queue, boolean autoAck, Consumer callback)
            参数：
                1. queue：队列名称
                2. autoAck：是否自动确认 ,类似咱们发短信,发送成功会收到一个确认消息
                3. callback：回调对象
             */
        // 消费者类似一个监听程序,主要是用来监听消息
        channel.basicConsume("simple_queue2",true,defaultConsumer);


        //可以不关闭资源  一直保持等待去队列取数据的状态


    }


    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //设置服务器ip
        connectionFactory.setHost("192.168.247.128");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置主机名
        connectionFactory.setVirtualHost("/");
        //用户名
        connectionFactory.setUsername("admin");
        //密码
        connectionFactory.setUsername("123456");


        Connection connection = connectionFactory.newConnection();

        return connection;
    }
}
