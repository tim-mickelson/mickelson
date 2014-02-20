package eu.mickelson.web.spring.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SecurityProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationBean authenticationBean = new AuthenticationBean();
		String username = null;
		String password = null;
		if(authentication.getCredentials()!=null){
			Object creds = authentication.getCredentials();
			// First is for Basic HTTP Auth, second for normal web auth
			if(creds instanceof UsernamePasswordAuthenticationToken){
				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)creds;
				if(token.getPrincipal()!=null&&token.getPrincipal() instanceof String)
					username = (String)token.getPrincipal();
				if(token.getCredentials()!=null&&token.getCredentials() instanceof String)
					password = (String)token.getCredentials();
			}else if(creds instanceof CredentialsBean){
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
		boolean support = (AuthenticationBean.class.isAssignableFrom(authentication));
		if(!support)
			support = (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
		return support;
	}

}  // end class SecurityProvider
