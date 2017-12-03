package com.ct.maven.SecKill.dao;

import org.apache.ibatis.annotations.Param;

import com.ct.maven.SecKill.entity.SuccessKilled;

/**
 * @author Administrator
 *
 */
public interface SuccessKilledDao {
	
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return  
	 * 插入的行数 ，本条小于等于1.
	 * 可以判断是否插入成功
	 */
	public int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	
	
	/**
	 * 查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 * @return
	 */
	public SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
