package com.youzhixu.component.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * @author huisman
 * @createAt 2015年6月11日 上午10:24:25
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu All Rights Reserved.
 */
public class ClusterTest {

	public static void main(String[] args) {
		String url =
				"failover:(tcp://127.0.0.1:61610,tcp://127.0.0.1:61611,tcp://127.0.0.1:61612)?"
						+ "maxReconnectAttempts=3&initialReconnectDelay=1000";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String password = "";
		String user = "";
		ActiveMQConnectionFactory connectionFactory =
				new ActiveMQConnectionFactory(user, password, url);
		sendQueue(connectionFactory, "test-node", "你好。。。啊");

	}

	private static void sendQueue(ActiveMQConnectionFactory connectionFactory, String dest,
			String message) {
		long start = System.currentTimeMillis();
		Connection connection = null;
		Session session = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue destination = session.createQueue(dest);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != session) {
					session.close();
				}
				if (null != connection) {
					connection.close();
				}
			} catch (Throwable ignore) {

			}
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时（毫秒）=============》" + (end - start));
	}
}
