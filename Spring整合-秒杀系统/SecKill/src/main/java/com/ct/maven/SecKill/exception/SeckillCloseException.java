package com.ct.maven.SecKill.exception;

/**
 * @author Administrator
 *	秒杀关闭异常
 */
@SuppressWarnings("serial")
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillCloseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
