package tank.myhttp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import tank.myhttp.api.IHttpHandler;

/**
 * @author tank
 * @date:2014-4-16 下午05:20:38
 * @description:
 * @version :
 */
@Component
public class HttpAppContext {
	private static Logger logger = LoggerFactory.getLogger(HttpAppContext.class);
	private static Map<String, IHttpHandler> httpHandlers = new HashMap<String, IHttpHandler>();

	public HttpAppContext() {

	}

	public static void init(AbstractApplicationContext appContext) {

		Map<String, IHttpHandler> handlers = appContext.getBeansOfType(IHttpHandler.class);
		if (handlers != null && !handlers.isEmpty()) {
			for (IHttpHandler handler : handlers.values()) {
				if (httpHandlers.containsKey(handler.getUrl())) {
					logger.error("存在相同KEY的handler{}", handler.getUrl());
					System.exit(0);// TODO:也可以去掉
				}
				httpHandlers.put(handler.getUrl(), handler);
			}
		} else {
			logger.warn("找有没到任务http handler实现 ");
		}

	}

	public static IHttpHandler getHttpHandler(String url) {
		return httpHandlers.get(url);
	}

}
