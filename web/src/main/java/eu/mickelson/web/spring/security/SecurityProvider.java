package eu.mickelson.web.spring.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SecurityProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationBean authenticationBean = new AuthenticationBean();
		//if(authentication.getCredentials()!=null&&authentication.getCredentials()instanceof UserCredentials){
		if(authentication.getCredentials()!=null&&authentication.getCredentials()instanceof CredentialsBean){
			CredentialsBean credentials = (CredentialsBean)authentication.getCredentials();
			authenticationBean.setAuthenticated(credentials.getUsername().equals("pippo"));
		}
		
		return authenticationBean;
	}  // end public function authenticate

	@Override
	public boolean supports(Class<?> authentication) {
		return (AuthenticationBean.class.isAssignableFrom(authentication));
	}

}
