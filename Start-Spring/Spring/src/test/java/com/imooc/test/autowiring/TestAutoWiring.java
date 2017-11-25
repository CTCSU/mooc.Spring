package com.imooc.test.autowiring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.imooc.autowiring.AutoWiringService;
import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAutoWiring extends UnitTestBase {
	
	public TestAutoWiring() {
		super("classpath:spring-autowiring.xml");
	}
	
	@Test
	public void testSay() {
		AutoWiringService service = super.getBean("autoWiringService");
		service.say(" this is a test");
	}

}
