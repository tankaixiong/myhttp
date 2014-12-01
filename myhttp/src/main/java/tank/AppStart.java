package tank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tank.myhttp.HttpAppContext;

/**
 * @author tank
 * @date:1 Dec 2014 09:51:32
 * @description:
 * @version :1.0
 */
public class AppStart {
	private static Logger logger = LoggerFactory.getLogger(AppStart.class);
	
	public static void main(String[] args) {
		final AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-mina.xml");
		ctx.registerShutdownHook();
		
		
		HttpAppContext.init(ctx);
 

		logger.info("添加JVM关闭hook!");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("程序关闭");
			 
				ctx.close();
			}
		});

	}

}
