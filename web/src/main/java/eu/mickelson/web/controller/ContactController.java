package eu.mickelson.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.mickelson.web.contact.beans.ContactBean;

@Controller
@RequestMapping("/contacts")
public class ContactController {

	@RequestMapping(value="/contactList", method = RequestMethod.GET)
	@ResponseBody
	public ContactBean contactList(){
		System.out.println("kommer jag hit");
		ContactBean bean = new ContactBean();
		bean.setName("pippo");
		return bean;
	}
	
}  // end public controller ContactController