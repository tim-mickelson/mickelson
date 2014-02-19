package eu.mickelson.web.spring.context;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import eu.mickelson.web.spring.config.SecurityConfig;
import eu.mickelson.web.spring.filter.SecurityFilter;
import eu.mickelson.web.spring.security.SecurityProvider;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	
	
/*	
	@Autowired
	SecurityProvider securityProvider;
	@Override
	 protected void afterSpringSecurityFilterChain(ServletContext servletContext){
		SecurityFilter securityFilter = new SecurityFilter();
		insertFilters(servletContext, securityFilter);
	}
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext){
		SecurityFilter securityFilter = new SecurityFilter();
		insertFilters(servletContext, securityFilter);
	}

*/
	
}  // end public class SecurityWebApplicationInitializer