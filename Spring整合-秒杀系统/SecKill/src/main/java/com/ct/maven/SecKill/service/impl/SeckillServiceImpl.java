package com.ct.maven.SecKill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.ct.maven.SecKill.dao.SeckillDao;
import com.ct.maven.SecKill.dao.SuccessKilledDao;
import com.ct.maven.SecKill.dao.SuccessKilledDaoTest;
import com.ct.maven.SecKill.dao.cache.RedisDao;
import com.ct.maven.SecKill.dto.Exposer;
import com.ct.maven.SecKill.dto.SeckillExecution;
import com.ct.maven.SecKill.entity.Seckill;
import com.ct.maven.SecKill.entity.SuccessKilled;
import com.ct.maven.SecKill.enums.SeckillStateEnum;
import com.ct.maven.SecKill.exception.RepeatKillException;
import com.ct.maven.SecKill.exception.SeckillCloseException;
import com.ct.maven.SecKill.exception.SeckillException;
import com.ct.maven.SecKill.service.SeckillService;

@Component
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

	// 注入Service依赖
	@Autowired
	// @Resource,@Inject
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	@Autowired
	private RedisDao redisDao;

	// md5盐值字符串
	private final String slat = "asdwdasdwadasdw@#12sdwd$2312AQD";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// 优化点：缓存优化，一致性：建立在超时的基础上

		/*
		 * get from cache if null get db put cache
		 */
		// 1:访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			// 2:访问数据库
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				// 3：访问数据库
				redisDao.putSeckill(seckill);
			}
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 系统当前时间
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime()
				|| nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(),
					startTime.getTime(), endTime.getTime());
		}

		// 转化特定字符串的过程，不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);

	}

	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, SeckillCloseException,
			RepeatKillException {

		try {
			if (md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			// 执行秒杀逻辑:减库存，记录购买行为
			// 记录购买行为，先插入在更新记录。插入导致锁定的情况比较少
			int insertCount = successKilledDao.insertSuccessKilled(seckillId,
					userPhone);

			// 唯一：seckillId，userPhone
			if (insertCount <= 0) {
				throw new RepeatKillException("seckill repeated");
			} else {
				Date nowTime = new Date();
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					// 没有更新到记录,秒杀结束,rollback
					throw new SeckillCloseException("seckill is closed");
				}
				// 秒杀成功
				SuccessKilled successKilled = successKilledDao
						.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId,
						SeckillStateEnum.Success, successKilled);
			}

		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}

	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	public SeckillExecution executeSeckillProcedure(long seckillId,
			long userPhone, String md5) {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			return new SeckillExecution(seckillId,SeckillStateEnum.DATE_REWRITE);
		}
		Date killTime = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("seckillId", seckillId);
		map.put("phone",userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		
		//执行存储过程，result被赋值
		try{
			seckillDao.killByProcedure(map);
			//获取result
			int result = MapUtils.getInteger(map, "result" ,-2);
			if(result == 1){
				SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.Success, successKilled);
			}else{
				return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
		
	}

}
