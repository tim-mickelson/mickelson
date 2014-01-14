package eu.mickelson.jpa.test.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=false)
public class JPAConfig {

	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource ds = new DriverManagerDataSource();
		 
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/mickelson");
		ds.setUsername("root");
		ds.setPassword("maria*db");
 
        return ds;
    }  // end function dataSource

	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
 
        lcemfb.setDataSource(this.dataSource());
        lcemfb.setPackagesToScan(new String[] {"eu.mickelson.jpa.entities"});
        lcemfb.setPersistenceUnitName("MyTestPU");
 
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        lcemfb.setJpaVendorAdapter(va);
 
        Properties ps = new Properties();
        ps.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        ps.put("hibernate.format_sql", "true");
        ps.put("hibernate.show_sql", "false");
        lcemfb.setJpaProperties(ps);
 
        lcemfb.afterPropertiesSet();
        return lcemfb;
    }  // end function entityManagerFactoryBean
	
	@Bean
	public SharedEntityManagerBean entityManager(){
		SharedEntityManagerBean entityManager = new SharedEntityManagerBean();
		entityManager.setEntityManagerFactory(this.entityManagerFactoryBean().getNativeEntityManagerFactory());
		return entityManager;
	} 
	
	@Bean
	public PlatformTransactionManager transactionManager(){
	    JpaTransactionManager tm = new JpaTransactionManager();
	    tm.setEntityManagerFactory(this.entityManagerFactoryBean().getObject() );
	    return tm;
	}	// end function transactionManager

	@Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }	
	
}  // end public class JPAConfig