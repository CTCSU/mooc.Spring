package com.imooc.test.resource;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.imooc.resource.MoocResource;
import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestResource extends UnitTestBase {
	
	public TestResource() {
		super("classpath:spring-resource.xml");
	}
	
	@Test
	public void testResource() {
		MoocResource resource = super.getBean("moocResource");
		try {
			resource.resource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
