package com.atguigu.rabbitmq.simple2;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//生产者
public class Producer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //得到连接
        Connection connection = Producer2.getConnection();

        //创建信号
        Channel channel = connection.createChannel();

        //声明队列
        // 5个参数   1）队列名称  2）是否持久化队列 重启mq 队列还在  3）是否独占整个连接（一个连接只存在一个监听的消费者） 当连接关闭时，如果是独占就关闭队列
        // 4）是否在不使用消费者时时 删除队列  5）传个map集合 里面存放键值对 设置队列的参数
        channel.queueDeclare("simple_queue2",true,false,false,null);

        String msg = "发送消息";

        /**
         *    把消息发送到交换机  由交换值转发到指定的 消息队列上
         *
         * 参数1：交换机名称,如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：配置信息
         * 参数4：消息内容
         */
        channel.basicPublish("","simple_queue2",null,msg.getBytes());

        System.out.println("消息已发送傻逼12345");

        //关闭连接
        channel.close();
        connection.close();





    }



    //获取连接
    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置端口号
        connectionFactory.setPort(5672);
        //设置主机地址
        connectionFactory.setHost("192.168.247.128");
        //设置主机名称
        connectionFactory.setVirtualHost("/");
        //设置登录用户名
        connectionFactory.setUsername("admin");
        //填写密码
        connectionFactory.setPassword("123456");


        //得到连接
        Connection connection = connectionFactory.newConnection();

        return connection;

    }
}
