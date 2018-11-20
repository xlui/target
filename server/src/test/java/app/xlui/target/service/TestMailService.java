package app.xlui.target.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMailService {
	@Autowired
	private MailService mailService;
	@Autowired
	private Environment environment;

	@Test
	public void testSimpleMail() throws Exception {
		Assert.assertNotNull(environment.getProperty("spring.mail.username"));
//		mailService.sendSimpleMail("atliuqi@qq.com", "Test Simple Mail", "This is a simple mail sent from spring boot.\nCurrent time is: " + new Date());
	}

	@Test
	public void testHtmlMail() throws Exception {
		Assert.assertNotNull(environment.getProperty("spring.mail.password"));
		String content = "<html>\n" +
				"<body>\n" +
				"	<h3>hello, this is a HTML mail from spring boot</h3>\n" +
				"</body>\n" +
				"</html>";
//		mailService.sendHtmlMail("atliuqi@qq.com", "Test HTML Mail", content);
	}
}
