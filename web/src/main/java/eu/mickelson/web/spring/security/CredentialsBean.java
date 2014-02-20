package eu.mickelson.web.spring.security;

import java.io.Serializable;

/**
 * On login this is the container for credential attributes as username and password, etc.
 * 
 * @author Tim Mickelson
 * @since 14/01/2014
 */
public class CredentialsBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}  // end public class CredentialBean