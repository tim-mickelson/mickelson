package eu.mickelson.test.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.mickelson.test.config.JmsConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ JmsConfig.class})
public class TestJms {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	JmsSender jmsSender;
	
	@Test
	public void sendJms(){
		String message = "say something";
		logger.debug("message: "+message);
		jmsSender.sendText(message);
	}
	
}  // end public class TestJms