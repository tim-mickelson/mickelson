package eu.mickelson.web.spring.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import eu.mickelson.web.spring.security.AuthenticationBean;
import eu.mickelson.web.spring.security.CredentialsBean;

public class SecurityFilter extends GenericFilterBean {
	Logger logger = LoggerFactory.getLogger(getClass());
	//@Autowired
	AuthenticationManager authenticationManager;
	
	public void setAuthenticationManager(AuthenticationManager authenticationManager){
		logger.debug(authenticationManager.toString());
		logger.debug(this.toString());
		this.authenticationManager = authenticationManager;
	}
	
	public enum AUTH{
		username, password
	}
	
	public SecurityFilter(){
		super();
		logger.debug("Create filter");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		logger.debug(this.toString());
		if(authenticationManager==null){
			logger.debug("authenticationManager null");
			chain.doFilter(request, response);
			return;
		}
		logger.debug("authenticationManager not null");
		String username = null;
		String password = null;
		
		if(request instanceof HttpServletRequest ){
			// Only attempt to get the parameters if in POST to avoid user putting in URL the params
			if( ((HttpServletRequest)request).getMethod().equals("POST")){
				username = request.getParameter(AUTH.username.toString());
				password = request.getParameter(AUTH.password.toString());
			}
			HttpSession session = ((HttpServletRequest)request).getSession();
			username = username!=null?username:(String)session.getAttribute(AUTH.username.toString()); 
			password = password!=null ?password:(String)session.getAttribute(AUTH.password.toString()); 
		}
		if (SecurityContextHolder.getContext().getAuthentication() == null){		
			CredentialsBean user = null;
			if(username!=null&&password!=null){
				user = new CredentialsBean();
				user.setPassword(password);
				user.setUsername(username);
			}
			AuthenticationBean authenticationBean = new AuthenticationBean();
			authenticationBean.setCredentials(user); //.setCredential(user);
			Authentication authentication = authenticationManager.authenticate(authenticationBean);
			if(authentication!=null&&authentication.isAuthenticated()){
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}else{
				logger.debug("User already authenticated");
		}
		chain.doFilter(request, response);
	}  // end doFilter

}  // end public class SecurityFilter