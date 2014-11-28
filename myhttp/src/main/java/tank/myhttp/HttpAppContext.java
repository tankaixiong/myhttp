package tank.myhttp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

import tank.myhttp.api.IHttpHandler;

/**
 * @author tank
 * @date:2014-4-16 下午05:20:38
 * @description:
 * @version :
 */
public class HttpAppContext {
	private static Logger log = LoggerFactory.getLogger(HttpAppContext.class);
	private static Map<String, IHttpHandler> httpHandlers;

	private static AbstractApplicationContext appContext;

	public void init(AbstractApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
	 * 获得系统支持的所有Handlers(消息处理程序)
	 */
	public static Map<String, IHttpHandler> getHttpHandlers() {

		if (httpHandlers == null) {
			Map<String, IHttpHandler> handlers = appContext.getBeansOfType(IHttpHandler.class);
			if (handlers != null && handlers.size() > 0) {
				httpHandlers = new HashMap<String, IHttpHandler>();
				for (IHttpHandler handler : handlers.values()) {
					if (httpHandlers.containsKey(handler.getUrl())) {
						log.error("存在相同KEY的handler{}", handler.getUrl());
						System.exit(0);// TODO:也可以去掉
					}
					httpHandlers.put(handler.getUrl(), handler);
				}
			}
		}
		return httpHandlers;
	}

	public static IHttpHandler getHttpHandler(String url) {
		return getHttpHandlers() == null ? null : getHttpHandlers().get(url);
	}

}
