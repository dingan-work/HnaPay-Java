package com.gbi.hnapay.service;

import com.gbi.hnapay.bean.request.accountmanage.CardBindRequest;
import com.gbi.hnapay.bean.result.accountmanage.CardBindConfirmResult;

/**
 * 账户管理相关
 *
 * @author ganhua
 */
public interface AccountManageService extends BaseRequestService {

	/**
	 * 绑卡接口
	 *
	 * @param request 请求对象
	 * @return 新生返回的签约订单号
	 */
	String cardBinding(CardBindRequest request);

	/**
	 * 绑卡确认接口
	 *
	 * @param ncountOrderId 签约订单号
	 * @param smsCode       签约短信验证码
	 * @return 数据对象
	 */
	CardBindConfirmResult cardBindConfirm(String ncountOrderId, String smsCode);

	/**
	 * 个人用户解绑接口
	 *
	 * @param oriBindCardAgrNo 原绑卡协议号
	 * @param userId           用户ID
	 * @return 是否解绑成功
	 */
	boolean individualUserUnbinding(String oriBindCardAgrNo, String userId);
}
