package com.imooc.test.aware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAware extends UnitTestBase {
	
	public TestAware() {
		super("classpath:spring-aware.xml");
	}
	
//	@Test
//	public void testMoocApplicationContext() {
//		System.out.println("testMoocApplicationContext : " + super.getBean("moocApplicationContext").hashCode());
//	}
	
	@Test
	public void textMoocBeanName() {
		System.out.println("textMoocBeanName : " + super.getBean("moocBeanName").hashCode());
	}
	
}
