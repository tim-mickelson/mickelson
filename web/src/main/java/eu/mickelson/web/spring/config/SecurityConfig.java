package eu.mickelson.web.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import eu.mickelson.web.spring.filter.SecurityFilter;
import eu.mickelson.web.spring.security.SecurityProvider;

/**
 * Configure the security for web application.
 * http://docs.spring.io/spring-security/site/docs/3.2.0.RC2/reference/htmlsingle/#abstractsecuritywebapplicationinitializer-with-spring-mvc
 * 
 * http://www.jiwhiz.com/post/2013/5/Spring_Application_without_XML_Config
 * 
 * @author Tim Mickelson
 * @since 14/01/2014
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("auth: "+auth.toString());
		//auth.authenticationProvider(securityProvider()).build();
		auth.authenticationProvider(securityProvider());	 
	}  // end public configureGlobal
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception{
		 SecurityFilter filter = new SecurityFilter();
		 filter.setAuthenticationManager(authenticationManager());

		 logger.debug("http: "+http.toString());
		 // http://www.petrikainulainen.net/programming/spring-framework/adding-social-sign-in-to-a-spring-mvc-web-application-configuration/
		 http.csrf().disable();
		 
		 http
		 	.authorizeRequests().antMatchers("/login.html").permitAll()
		 .and()
		 	.formLogin().loginPage("/login.html")
		 .and()
		 	.logout().logoutUrl("/logout")
		 .and()
		 	.authorizeRequests().anyRequest().authenticated()
		 .and()
		 	.authenticationProvider(new SecurityProvider())
		 	.addFilterBefore( filter, AbstractPreAuthenticatedProcessingFilter.class);
		 
		 	//.addFilterBefore( myFilter(), UsernamePasswordAuthenticationFilter.class);
		 /*
		 http.authorizeRequests()
		 	.anyRequest().authenticated()
		 	.and()
		 	.formLogin()
		 	.and()
		 	.addFilterAfter( new SecurityFilter(), AbstractPreAuthenticatedProcessingFilter.class);
		 */
	 } // end protected function configure
	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 super.configure(auth);
		 logger.debug(auth.toString());
	 }
	 
	 @Override
	 public void configure(WebSecurity web) throws Exception {
		 super.configure(web);
		 logger.debug(web.toString());
	 }
	
	 @Bean
	 public SecurityProvider securityProvider(){
		 return new SecurityProvider();
	 }

/*	 
	 @Bean
	 SecurityFilter myFilter() throws Exception{
		 SecurityFilter filter = new SecurityFilter();
		 filter.setAuthenticationManager(authenticationManager());
		 return filter;
	 }
*/
	 /*	 
	 @Bean
	 SecurityFilter springSecurityFilterChain3(){
		 return new SecurityFilter();
	 }
*/	 
	 @Bean
	 public ProviderManager providerManager(){
		 List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
		 list.add(securityProvider());
		 return new ProviderManager(list);
	 }
	 
}  // end public class SecurityConfig