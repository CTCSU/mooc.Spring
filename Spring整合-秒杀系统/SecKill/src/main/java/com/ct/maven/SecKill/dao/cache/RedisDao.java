package com.ct.maven.SecKill.dao.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.ct.maven.SecKill.entity.Seckill;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class RedisDao {
	private JedisPool jedisPool;
	 
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public Seckill getSeckill(long seckillId){
		try{
			Jedis jedis = jedisPool.getResource();
			try{
				String key = "seckill:"+seckillId;
				//并没有实现内部序列化操作
				//get->byte[]->反序列化->Object(Seckill)
				//加快序列化操作
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes != null){
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
				
			}finally{
				jedis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		//set Object(Seckill)->序列化->byte[]
		try{
			Jedis jedis = jedisPool.getResource();
			try{
				String key = "seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeout = 60 * 60;
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			}finally{
				jedis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
