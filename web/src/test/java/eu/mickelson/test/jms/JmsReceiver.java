package eu.mickelson.test.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsReceiver implements MessageListener{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onMessage(Message message) {
		logger.debug("Received: " + message);
	}
}  // end public class JmsReceiver