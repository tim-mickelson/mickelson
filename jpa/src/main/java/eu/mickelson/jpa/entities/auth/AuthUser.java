package eu.mickelson.jpa.entities.auth;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Tim Mickelson
 *
 */
@Entity
@Table(name="auth_user")
public class AuthUser implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer iduser;
	@Column(nullable=false)
	String username;
	@Column(nullable=false)
	String password;
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	Date dt_insert = new Date();
	
	public Integer getIduser() {
		return iduser;
	}
	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}
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
	public Date getDt_insert() {
		return dt_insert;
	}
	public void setDt_insert(Date dt_insert) {
		this.dt_insert = dt_insert;
	}
	
}  // end public class AuthUser