package cn.spring.demo2;

import javax.annotation.Resource;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext2.xml")
public class SpringDemo2 {
	
	@Resource(name="accountService")
	private	AccountService accountService;
	
	@Resource(name="accountServiceProxy")
	private AccountService accountServiceProxy;
	
	@Test
	public void demo2(){
		accountServiceProxy.transfer("aaa", "bbb", 200.0);
	}
}
