package wiki.ganhua.hnapay.service;

import wiki.ganhua.hnapay.bean.request.openaccount.IndividualOpenAccountRequest;
import wiki.ganhua.hnapay.bean.result.openaccount.QueryAccountResult;

/**
 * 新生开户相关 API
 *
 * @author ganhua
 * created on 2022-12-02
 */
public interface OpenAccountService extends BaseRequestService {

	/**
	 * 个人开户接口（新）
	 *
	 * @param request 请求对象
	 * @return 新生返回的用户编号
	 */
	String individualOpenAccount(IndividualOpenAccountRequest request);

	/**
	 * 用户查询接口
	 *
	 * @param userId     用户ID
	 * @param merOrderId 商户订单号
	 * @return 数据对象
	 */
	QueryAccountResult queryAccount(String merOrderId, String userId);
}
