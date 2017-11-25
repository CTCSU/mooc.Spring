package com.imooc.test.lifecycle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanLifecycle extends UnitTestBase {
	
	public TestBeanLifecycle() {
		super("classpath:spring-lifecycle.xml");
	}
	
	@Test
	public void test1() {
		super.getBean("beanLifeCycle");
	}
	
}
