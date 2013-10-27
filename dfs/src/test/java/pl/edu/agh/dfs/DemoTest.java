package pl.edu.agh.dfs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/config/spring-test-config.xml")
public class DemoTest {

	@Test
	public void demoTest() {
		String demo = "demo";
		Assert.assertEquals("demo", demo);
	}
}
