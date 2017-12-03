package com.ct.maven.SecKill.dao;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ct.maven.SecKill.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件

@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

	@Resource
	SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		long id=10L;
		long phone=15512315131L;
		int insertCount = successKilledDao.insertSuccessKilled(id, phone);
		System.out.println("insertCount = " + insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long id=10L;
		long phone = 15512315131L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}

}
