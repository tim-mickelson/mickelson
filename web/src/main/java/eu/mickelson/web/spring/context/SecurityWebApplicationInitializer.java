package eu.mickelson.web.spring.context;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import eu.mickelson.web.spring.config.SecurityConfig;
import eu.mickelson.web.spring.filter.SecurityFilter;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
	@Override
	 protected void afterSpringSecurityFilterChain(ServletContext servletContext){
		SecurityFilter securityFilter = new SecurityFilter();
		insertFilters(servletContext, securityFilter);
	}
	
}  // end public class SecurityWebApplicationInitializer