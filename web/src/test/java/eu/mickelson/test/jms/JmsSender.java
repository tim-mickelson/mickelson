package eu.mickelson.test.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsSender {
	@Autowired
	JmsTemplate jmsTemplate;
	
	 public void sendText(final String text) {
		 jmsTemplate.send(new MessageCreator() {
			 public Message createMessage(Session session) throws JMSException {
				 return session.createTextMessage(text);
			 }
		 });
	 }	// end function sendText

}  // end public class JmsSender