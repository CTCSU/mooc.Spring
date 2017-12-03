package com.ct.maven.SecKill.enums;

/**
 * @author Administrator
 *	使用美剧表述常量数据字段
 */
public enum SeckillStateEnum {
	Success(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATE_REWRITE(-3,"数据篡改")
	;
	private int state;
	
	private String stateInfo;

	public int getState() {
		return state;
	}

	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillStateEnum stateOf(int index){
		for(SeckillStateEnum state:values()){
			if(state.getState() == index){
				return state;
			}
		}
		return null;
	}
	
}
