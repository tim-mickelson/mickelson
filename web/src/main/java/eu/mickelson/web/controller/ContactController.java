package eu.mickelson.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.mickelson.web.contact.beans.ContactBean;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/contactList", method = RequestMethod.GET)
	@ResponseBody
	public ContactBean contactList(){
		logger.debug("kommer jag hit");
		Object a = SecurityContextHolder.getContext().getAuthentication();
		Object o = SecurityContextHolder.getContext().getAuthentication().getDetails();
		
		ContactBean bean = new ContactBean();
		bean.setName("pippo");
		return bean;
	}
	
}  // end public controller ContactController