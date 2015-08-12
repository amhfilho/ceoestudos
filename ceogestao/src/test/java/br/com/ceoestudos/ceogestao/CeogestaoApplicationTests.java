package br.com.ceoestudos.ceogestao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = H2JpaConfig.class)
@WebAppConfiguration
@DatabaseSetup(value="sampledata.xml", type=DatabaseOperation.INSERT)
@DatabaseTearDown(value="sampledata.xml", type=DatabaseOperation.DELETE)
@Transactional
public class CeogestaoApplicationTests {

	@PersistenceContext
	EntityManager em;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(em);
	}
	

}
