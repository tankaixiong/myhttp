package tank.myhttp;

import java.io.IOException;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.HttpResponseImpl;
import org.apache.mina.http.api.HttpEndOfContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @author tank
 * @version :1.0
 * @date:Oct 24, 2012 10:21:07 AM
 * @description:HTTP网关分发-消息处理器
 */
@Controller
public class TransformHttpRquestAdapter extends IoHandlerAdapter {
	private Logger log = LoggerFactory.getLogger(TransformHttpRquestAdapter.class);

	/**
	 * 当一个客户端连接进入时
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("sessionOpened");
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 12);
	}

	/**
	 * 当一个客户端关闭时
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("sessionClosed");
	}

	/**
	 * 异常时断开连接
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

		log.error("服务端处理产生异常:{}", cause);
		session.close(false);
	}

	/**
	 * 当接收到客户端的信息
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		log.info("messageReceived收到消息:{}", message);
		if (message instanceof HttpEndOfContent) {
			HttpEndOfContent endContent = (HttpEndOfContent) message;
			log.info("收到HttpEndOfContent: {}", endContent);

		} else if (message instanceof HttpRequestImpl) {
			HttpRequestImpl request = (HttpRequestImpl) message;
			log.info("收到HttpRequestImpl:{}", request.getRequestPath());

			HttpResponseImpl response = ResponseFactory.getResponse();
			String sid = request.getParameter("sid");
			if (sid == null) {
				response.appendBody(Ret.PARAM_ERROR.toJson());
			} else {
				//当sid=-9110表示全服广播时，进行sign验证,其它则转至各自服自行验证
				String []sids=sid.split(",");
				for (String serverId : sids) {
					//分服发送请求消息
					//ServerInfo server = ServerMonitor.getServer(serverId);
					//TranceRequst(request, response, server);
				}
//				if ("-9110".equals(sid)) {
//					String sign = request.getParameter("sign");
//					if (sign != null && SigCheck.verifySig(request.getSimpleParameters(), sign)) {
//						List<ServerInfo> serverList = ServerMonitor.getServers(ServerType.WORLD_SERVER);
//						for (ServerInfo wserver : serverList) {
//							TranceRequst(request, response, wserver);
//						}
//					} else {
//						response.appendBody(Ret.PARAM_ERROR.toJson());
//					}
//
//				} else {
//					//分服发送请求消息
//					ServerInfo server = ServerMonitor.getServer(sid);
//					TranceRequst(request, response, server);
//				}
			}
			//响应给客户端
			session.write(response).addListener(IoFutureListener.CLOSE);

		}

	}

//	private void TranceRequst(HttpRequestImpl request, HttpResponseImpl response, ServerInfo server) {
//		if (server == null) {
//			response.appendBody(Ret.PARAM_ERROR.toJson());
//		} else {
//			String domain = server.getDomain();
//			try {
//				String resStr = HttpUtils.getHttpRequest(domain + request.getRequestPath() + "?" + request.getQueryString());
//				response.appendBody(resStr);
//			} catch (IOException e) {
//				log.error("{}", e);
//				response.appendBody(Ret.BUSY.toJson());
//			}
//
//		}
//	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.close(false);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.info("发送数据:{}", message);
		super.messageSent(session, message);
	}

}
