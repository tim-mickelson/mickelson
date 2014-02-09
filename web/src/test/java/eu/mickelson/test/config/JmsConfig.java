package eu.mickelson.test.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import eu.mickelson.test.jms.JmsReceiver;
import eu.mickelson.test.jms.JmsSender;

@Configuration
public class JmsConfig {
	
	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setDefaultDestination(new ActiveMQQueue("jms.queue"));
		jmsTemplate.setConnectionFactory(connectionFactory());
		return jmsTemplate;
	}
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		//activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
		activeMQConnectionFactory.setBrokerURL("vm://embedded?broker.persistent=false");
		return activeMQConnectionFactory;
	}
	
	@Bean
	 public DefaultMessageListenerContainer messageListenerContainer() {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory());
		messageListenerContainer.setDestinationName("jms.queue");
		messageListenerContainer.setMessageListener(jmsReceiver());
		return messageListenerContainer;
	}	

	@Bean
	public JmsSender jmsSender(){
		JmsSender jmsSender = new JmsSender();
		return jmsSender;
	}
	
	@Bean
	public JmsReceiver jmsReceiver(){
		return new JmsReceiver();
	}
	
}  // end public class JmsConfig