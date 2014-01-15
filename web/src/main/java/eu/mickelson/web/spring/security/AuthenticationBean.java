package eu.mickelson.web.spring.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationBean implements Authentication {
	private static final long serialVersionUID = 1L;

	private boolean authenticated = false;
	private CredentialsBean credentials;
	private Collection<GrantedAuthority> authorities;
	private AgentBean agent;
	private String name;

	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities){
		this.authorities = authorities;
	}
		
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setCredentials(CredentialsBean credentials){
		this.credentials = credentials;
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}

	public void setDetails(AgentBean agent){
		this.agent = agent;
	}
	
	@Override
	public Object getDetails() {
		return agent;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated = authenticated;
	}

}
