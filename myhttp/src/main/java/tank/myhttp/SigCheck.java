package tank.myhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
		* @author tank
		* @date:2014-5-8 下午05:40:13
		* @description:Gm 验证SIGN 防篡改
		* @version :
		*/
public class SigCheck {
	private static final Logger log = LoggerFactory.getLogger(SigCheck.class);
	private static final String keyName = "server.gm.sign";

	public static boolean verifySig(Map<String, String> params, String sign) {
		// 确保不含sig
		params.remove("sign");
		//解码url
		try {
			codeParamValue(params);
		} catch (UnsupportedEncodingException e) {
			log.error("{}", e);
			return false;
		}
		String serverSign = makeSign(params);

		if (serverSign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	private static String makeSign(Map<String, String> params) {
		//按字典排序
		Object[] keys = params.keySet().toArray();
		Arrays.sort(keys);

		StringBuilder buffer2 = new StringBuilder();

		for (int i = 0; i < keys.length; i++) {
			buffer2.append(keys[i]).append("=").append(params.get(keys[i]));

			//if (i != keys.length - 1) {
			buffer2.append("&");
			//}
		}
		String serverSign = org.apache.commons.codec.digest.DigestUtils.md5Hex(buffer2 + "_k_e_y-NAME%21");
		return serverSign;
	}

	public static void codeParamValue(Map<String, String> params) throws UnsupportedEncodingException {
		Set<String> keySet = params.keySet();
		Iterator<String> itr = keySet.iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			String value = (String) params.get(key);
			value = URLDecoder.decode(value, "UTF-8");
			params.put(key, value);
		}
	}

	/**
	 * 用于测试时查看sign
	 * @param url
	 * @return
	 */
	public static String makeSign(String url) {
		int paramIndex = url.indexOf("?");
		if (paramIndex != -1) {
			url = url.substring(paramIndex+1);
		}

		Map<String, String> params = getSimpleParameters(url);
		// 确保不含sig
		params.remove("sign");
		
		String serverSign = makeSign(params);
		log.info("生成 的sign{}", serverSign);
		return serverSign;
	}

	/**
	 * 返回参数,by tank
	 */
	private static Map<String, String> getSimpleParameters(String queryString) {
		Map<String, String> parameters = new HashMap<String, String>();
		String[] params = queryString.split("&");

		for (int i = 0; i < params.length; i++) {
			String[] param = params[i].split("=");
			String name = param[0];
			String value = param.length == 2 ? param[1] : "";

			parameters.put(name, value);
		}
		return parameters;
	}

}
