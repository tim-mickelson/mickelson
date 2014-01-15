package eu.mickelson.web.spring.security;

import eu.mickelson.jpa.entities.auth.AuthUser;

/**
 * Authenticated agent on platform.
 * 
 * @author Tim Mickelson
 * @since 14/01/2014
 */
public class AgentBean {
	private AuthUser authUser;

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}
		
}  // end public class AgentBean