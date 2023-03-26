package com.gbi.hnapay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.gbi.hnapay.bean.request.accountmanage.CardBindConfirmRequest;
import com.gbi.hnapay.bean.request.accountmanage.CardBindRequest;
import com.gbi.hnapay.bean.request.accountmanage.CardUnbindingRequest;
import com.gbi.hnapay.bean.result.accountmanage.CardBindConfirmResult;
import com.gbi.hnapay.bean.result.accountmanage.CardBindResult;
import com.gbi.hnapay.service.AccountManageService;
import com.gbi.hnapay.service.HnaPayService;
import com.gbi.hnapay.service.impl.base.BaseRequestImpl;
import lombok.RequiredArgsConstructor;

/**
 * @author ganhua
 */
@RequiredArgsConstructor
public class AccountManageServiceImpl extends BaseRequestImpl implements AccountManageService {

	private final HnaPayService hnaPayService;

	@Override
	public String cardBinding(CardBindRequest request) {
		String url = String.format("%s/r007.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		request.setMerUserIp(hnaPayService.getConfig().getMerUserIp());
		CardBindResult result = this.request(hnaPayService, request, CardBindResult.class);
		return result.getNcountOrderId();
	}

	@Override
	public CardBindConfirmResult cardBindConfirm(String ncountOrderId, String smsCode) {
		String url = String.format("%s/r008.htm", this.hnaPayService.getPayBaseUrl());
		CardBindConfirmRequest request = new CardBindConfirmRequest();
		request.setMerOrderId(IdUtil.getSnowflakeNextIdStr());
		request.setNcountOrderId(ncountOrderId);
		request.setSmsCode(smsCode);
		request.setUrl(url);
		request.setMerUserIp(hnaPayService.getConfig().getMerUserIp());
		return this.request(hnaPayService, request, CardBindConfirmResult.class);
	}

	@Override
	public boolean individualUserUnbinding(String oriBindCardAgrNo, String userId) {
		String url = String.format("%s/r009.htm", this.hnaPayService.getPayBaseUrl());
		CardUnbindingRequest request = new CardUnbindingRequest();
		request.setMerOrderId(IdUtil.getSnowflakeNextIdStr());
		request.setOriBindCardAgrNo(oriBindCardAgrNo);
		request.setUserId(userId);
		request.setUrl(url);
		return this.requestBool(hnaPayService, request);
	}
}
