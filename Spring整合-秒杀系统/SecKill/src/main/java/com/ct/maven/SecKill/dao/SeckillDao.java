package com.ct.maven.SecKill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ct.maven.SecKill.entity.Seckill;

public interface SeckillDao {
 
	/**
	 * 减库存,
	 * @param seckillId
	 * @param killTime
	 * @return 如果影响行数  >= 1 ，表示更新的记录行数
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * 根据Id查询seckill 对象
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 *根据偏移量查询秒杀商品列表 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	
	/**
	 * @param paraMap
	 * 存储过程进行秒杀
	 */
	void killByProcedure(Map<String,Object> paraMap);
	
}
