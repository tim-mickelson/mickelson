package eu.mickelson.web.controller;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.mickelson.web.contact.beans.ContactBean;

/**
 * Services intended to be exponed to Android application with JSON flow.
 * @author Tim Mickelson
 * @since 19/02/2014
 */
@Controller
@RequestMapping("/android")
public class AndroidController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/getContact", method = RequestMethod.GET)
	@ResponseBody
	public ContactBean getContact(){
		ContactBean bean = new ContactBean();
		bean.setName("TIM");
		bean.setSurname("MICKELSON");
		String beanValue = JSONString(bean);
		logger.debug(beanValue);
		return bean;
	} // end public function getContact
	
	/**
	 * Map object to JSON string.
	 * @author Tim Mickelson
	 * @since 21/01/2014
	 * @param bean
	 * @return
	 */
	public static String JSONString(Object bean){
		Logger l = LoggerFactory.getLogger(AndroidController.class);
		ObjectMapper mapper = new ObjectMapper();
		String beanValue = null;
		try {
			beanValue = mapper.writeValueAsString(bean);
		} catch (IOException e) {
			l.info(e.getLocalizedMessage());
		}
		beanValue = beanValue==null?"":beanValue;
		return beanValue;
	}
	
}  // end public class AndroidController