package com.uuabb.miao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoApplicationTests {

	@Test
	public void contextLoads() {
		BigDecimal bigDecimal=new BigDecimal("2.2");
		BigDecimal bigDecimal2=new BigDecimal("2.5");
		System.out.println(bigDecimal.add(bigDecimal2));

	}

}
