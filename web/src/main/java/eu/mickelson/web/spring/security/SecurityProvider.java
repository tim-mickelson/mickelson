package eu.mickelson.web.spring.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SecurityProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationBean authenticationBean = new AuthenticationBean();
		String username = null;
		String password = null;
		if(authentication!=null){
			Object creds = authentication.getCredentials();
			if(creds==null)
				return authenticationBean;
			// First is for Basic HTTP Auth, second for normal web auth
			if(creds instanceof CredentialsBean){
				username = ((CredentialsBean)creds).getUsername();
				password = ((CredentialsBean)creds).getPassword();
			}
			// Ok credentials are extracted
			if(username!=null){
				CredentialsBean bean = new CredentialsBean();
				bean.setUsername(username);
				bean.setPassword(password);
				authenticationBean.setAuthenticated(username.equals("pippo"));
				authenticationBean.setCredentials(bean);
			}
		}  // end credentials not null
		
		return authenticationBean;
	}  // end public function authenticate

	@Override
	public boolean supports(Class<?> authentication) {
		return (AuthenticationBean.class.isAssignableFrom(authentication));
	}

}  // end class SecurityProvider
