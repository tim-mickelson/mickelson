package eu.mickelson.jpa.test;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import eu.mickelson.jpa.entities.auth.AuthUser;
import eu.mickelson.jpa.test.config.JPAConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ JPAConfig.class})
@TransactionConfiguration(defaultRollback=true)
public class Authentication {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	EntityManager entityManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void test(){
		AuthUser user = new AuthUser();
		user.setUsername("pippo");
		user.setPassword("pluto");
		
		try{
			entityManager.persist(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info(user.getIduser().toString());
	} //
}  // end 