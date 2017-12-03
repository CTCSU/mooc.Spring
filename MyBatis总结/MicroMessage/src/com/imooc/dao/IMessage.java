package com.imooc.dao;

import java.util.List;

import com.imooc.bean.Message;

/**
 * 与Message配置文件相对应的接口
 */
public interface IMessage {
	/**
	 * 根据查询条件查询消息列表
	 */
	public List<Message> queryMessageList(Message message);
}
