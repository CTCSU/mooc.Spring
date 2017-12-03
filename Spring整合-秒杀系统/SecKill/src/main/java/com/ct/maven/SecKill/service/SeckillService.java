package com.ct.maven.SecKill.service;

import java.util.List;

import com.ct.maven.SecKill.dto.Exposer;
import com.ct.maven.SecKill.dto.SeckillExecution;
import com.ct.maven.SecKill.entity.Seckill;
import com.ct.maven.SecKill.exception.RepeatKillException;
import com.ct.maven.SecKill.exception.SeckillCloseException;
import com.ct.maven.SecKill.exception.SeckillException;


/**
 * @author Administrator
 *	业务接口： 站在”使用者“角度设计接口
 *	三个方面： 1 方法定义粒度；2：参数（简单）3：返回类型友好
 *
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单条秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开始时输出秒杀接口地址，
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException;
	
	
	SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
