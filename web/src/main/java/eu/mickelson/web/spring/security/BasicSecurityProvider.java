package eu.mickelson.web.spring.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class BasicSecurityProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationBean authenticationBean = null;
		String username = null;
		String password = null;
		if(authentication!=null){
			Object creds = authentication.getCredentials();
			if(creds==null)
				return authenticationBean;
			// First is for Basic HTTP Auth, second for normal web auth
			if(authentication instanceof UsernamePasswordAuthenticationToken){
				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
				if(token.getPrincipal()!=null&&token.getPrincipal() instanceof String)
					username = (String)token.getPrincipal();
				if(token.getCredentials()!=null&&token.getCredentials() instanceof String)
					password = (String)token.getCredentials();
			}
			// Ok credentials are extracted
			if(username!=null){
				if(username.equals("pippo")){
					authenticationBean = new AuthenticationBean();
					CredentialsBean bean = new CredentialsBean();
					bean.setUsername(username);
					bean.setPassword(password);
					authenticationBean.setCredentials(bean);
				}
			}
		}  // end credentials not null
		if(authenticationBean==null)
			throw new BadCredentialsException("wrong credentials");
		
		return authenticationBean;
	}  // end public function authenticate

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}  // end class SecurityProvider