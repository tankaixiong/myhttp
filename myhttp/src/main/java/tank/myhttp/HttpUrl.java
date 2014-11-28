package tank.myhttp;

/**
 * @author tank
 * @date:2014-4-16 下午05:45:04
 * @description:
 * @version :
 */
public class HttpUrl {
	/**
	 * 支付回调确认
	 */
	public static final String PAY_CONFIRM = "/slime/pay_confirm";

	public static final String TEST = "/test";

	/**
	 * 任务集市回调
	 */
	public static final String TASK_MARKET_CONFIRM = "/slime/check_award";

	// GM回调
	public static final String GM_BLINK = "/slime/gm_notice";

	// GM回调server地址
	// public static final String GM_WORLD_BLINK = "/slime/world/gm_blink";

	// 封停|解封
	public static final String GM_ACCOUNT = "/slime/gm_account";
	// 莫言
	public static final String GM_TALK = "/slime/gm_talk";
	
	public static final String TEST_HTTP = "/test/http";

	// 公告
	public static final String GM_NOTICE = "/slime/notice_msg";

	// 道具发放
	public static final String GM_DISPATCH_PROP = "/slime_dispatch_prop";

}
