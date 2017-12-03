package com.ct.maven.SecKill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ct.maven.SecKill.entity.Seckill;


/**
 * @author Administrator
 *	配置spring和junit整合，junit启东时加载springIoc容器
 *	spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件

@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(9, killTime);
		System.out.println(updateCount);
	}

	//java没有保存形参的记录
	@Test
	public void testQueryById() {
		long id = 9;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill.toString());
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for(Seckill seckill:seckills){
			System.out.println(seckill);
		}
	}

}
