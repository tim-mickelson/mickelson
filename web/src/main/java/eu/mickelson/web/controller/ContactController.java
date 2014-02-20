package eu.mickelson.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.mickelson.web.contact.beans.ContactBean;
import eu.mickelson.web.spring.security.AuthenticationBean;
import eu.mickelson.web.spring.security.CredentialsBean;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/contactList", method = RequestMethod.GET)
	@ResponseBody
	public ContactBean contactList(){
		logger.debug("kommer jag hit");
		String name = "name";
		String surname = "surname";
		Object o = SecurityContextHolder.getContext().getAuthentication();
		if(o instanceof AuthenticationBean){
			AuthenticationBean authBean = (AuthenticationBean)o;
			if(authBean.getCredentials()!=null&&authBean.getCredentials()instanceof CredentialsBean){
				CredentialsBean creds = (CredentialsBean)authBean.getCredentials();
				logger.debug("Username: "+creds.getUsername());
				name = creds.getUsername();
			}
		}
		
		ContactBean bean = new ContactBean();
		bean.setName(name);
		bean.setSurname(surname);
		return bean;
	}
	
}  // end public controller ContactController