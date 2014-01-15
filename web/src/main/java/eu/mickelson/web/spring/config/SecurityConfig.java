package eu.mickelson.web.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import eu.mickelson.web.spring.filter.SecurityFilter;
import eu.mickelson.web.spring.security.SecurityProvider;

/**
 * Configure the security for web application.
 * http://docs.spring.io/spring-security/site/docs/3.2.0.RC2/reference/htmlsingle/#abstractsecuritywebapplicationinitializer-with-spring-mvc
 * @author Tim Mickelson
 * @since 14/01/2014
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("pippo").password("pluto").roles("USER");
		 
		auth.authenticationProvider(securityProvider()).build();
		 
	 }  // end public configureGlobal
	 
	 @Override
	 protected void configure(HttpSecurity http) throws Exception{
		 http.authorizeRequests()
		 	.antMatchers("/login.html").permitAll()
		 	.anyRequest().authenticated()
		 	.and()
		 	.formLogin().and().addFilter(new SecurityFilter());
		 	//.formLogin().loginPage("/login.html").permitAll();
	 } // end protected function configure
	
	 @Bean
	 public SecurityProvider securityProvider(){
		 return new SecurityProvider();
	 }
	 	
}  // end public class SecurityConfig