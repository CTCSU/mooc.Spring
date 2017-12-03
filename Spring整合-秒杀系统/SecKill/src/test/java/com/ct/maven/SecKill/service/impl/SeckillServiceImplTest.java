package com.ct.maven.SecKill.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ct.maven.SecKill.dto.Exposer;
import com.ct.maven.SecKill.dto.SeckillExecution;
import com.ct.maven.SecKill.entity.Seckill;
import com.ct.maven.SecKill.exception.RepeatKillException;
import com.ct.maven.SecKill.exception.SeckillCloseException;
import com.ct.maven.SecKill.service.SeckillService;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件

@ContextConfiguration({ "classpath:spring/spring-dao.xml",
		"classpath:spring/spring-service.xml" })
public class SeckillServiceImplTest {

	private Logger logger = LoggerFactory.getLogger(SeckillServiceImplTest.class);
	
	@Autowired
	private SeckillService seckillServiceImpl;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillServiceImpl.getSeckillList();
		logger.info("list={}",list);
	}

	@Test
	public void testGetById() {
		long id = 9;
		Seckill seckill = seckillServiceImpl.getById(id);
		logger.info("seckill={}",seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long id = 9;
		Exposer exposer = seckillServiceImpl.exportSeckillUrl(id);
		logger.info("exposer={}",exposer);
	}

	@Test
	public void testExecuteSeckill() {
		long id = 9;
		long phone = 12354562123L;
		String md5 = "ebd5f44ec23765da6723d0dff199c177";
		SeckillExecution execution = seckillServiceImpl.executeSeckill(id, phone, md5);
		logger.info("SeckillExecution ={}",execution);
	}
	
	//测试代码完整逻辑，注意可重复执行
	@Test
	public void testSeckillLogic() throws Exception{
		long id = 9;
		long phone = 12354162526L;
		Exposer exposer =  seckillServiceImpl.exportSeckillUrl(id);
		if(exposer.isExposed()){
			try{
				SeckillExecution execution = seckillServiceImpl.executeSeckill(id, phone, exposer.getMd5());
				logger.info("SeckillExecution ={}",execution);
			} catch(RepeatKillException e){
				logger.error(e.getMessage());
			}catch(SeckillCloseException e1){
				logger.error(e1.getMessage());
			}
		}else{
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
	}
	
	@Test
	public void testExecuteSeckillProcedure(){
		long seckillId = 10;
		long phone = 12323423150L;
		Exposer exposer = seckillServiceImpl.exportSeckillUrl(seckillId);
		if(exposer.isExposed()){
			String md5 = exposer.getMd5();
			SeckillExecution execution = seckillServiceImpl.executeSeckillProcedure(seckillId, phone, md5);
			logger.info(execution.getStateInfo());
		}
	}
}
