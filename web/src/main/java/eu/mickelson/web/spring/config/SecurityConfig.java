package eu.mickelson.web.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import eu.mickelson.web.spring.filter.SecurityFilter;
import eu.mickelson.web.spring.security.BasicSecurityProvider;
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
public class SecurityConfig {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Configuration
	@Order(1)
	public static class BasicHttpSecurity extends WebSecurityConfigurerAdapter{
		@Autowired
		BasicSecurityProvider provider;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			BasicAuthenticationFilter filter = new BasicAuthenticationFilter(authenticationManager());
			http.csrf().disable();
			 http.antMatcher("/rest/**").authorizeRequests().anyRequest().authenticated()
			 .and()
			 	.httpBasic()
			 .and()
			 	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			 .and()
			 	.authenticationProvider(provider).addFilterBefore(filter, AbstractPreAuthenticatedProcessingFilter.class);
		
		}  // end function configure
	}  // end public static class BasicHttpSecurity
	
	@Configuration
	@Order(2)
	public static class WebSecurity extends WebSecurityConfigurerAdapter{
		@Autowired
		SecurityProvider provider;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			 SecurityFilter filter = new SecurityFilter();
			 filter.setAuthenticationManager(authenticationManager());
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
			 	.authenticationProvider(provider)
			 	.addFilterBefore( filter, AbstractPreAuthenticatedProcessingFilter.class);
			 
		} // end protected function configure
	}  // end public static class WebSecurity
	 
	 
	 @Bean
	 public SecurityProvider securityProvider(){
		 return new SecurityProvider();
	 }
	 
	 @Bean
	 public BasicSecurityProvider basicSecurityProvider(){
		 return new BasicSecurityProvider();
	 }

	 @Bean
	 public ProviderManager providerManager(){
		 List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
		 list.add(securityProvider());
		 return new ProviderManager(list);
	 }
	 
}  // end public class SecurityConfig