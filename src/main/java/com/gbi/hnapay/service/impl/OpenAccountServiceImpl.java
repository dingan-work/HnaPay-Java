package com.gbi.hnapay.service.impl;

import com.gbi.hnapay.bean.request.openaccount.IndividualOpenAccountRequest;
import com.gbi.hnapay.bean.request.openaccount.QueryAccountRequest;
import com.gbi.hnapay.bean.result.openaccount.IndividualResult;
import com.gbi.hnapay.bean.result.openaccount.QueryAccountResult;
import com.gbi.hnapay.service.HnaPayService;
import com.gbi.hnapay.service.OpenAccountService;
import com.gbi.hnapay.service.impl.base.BaseRequestImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author ganhua
 */
@RequiredArgsConstructor
@Slf4j
public class OpenAccountServiceImpl extends BaseRequestImpl implements OpenAccountService {

	private final HnaPayService hnaPayService;

	@Override
	public String individualOpenAccount(IndividualOpenAccountRequest request) {
		String url = String.format("%s/r010.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		return this.request(hnaPayService, request, IndividualResult.class).getUserId();
	}

	@Override
	public QueryAccountResult queryAccount(String merOrderId, String userId) {
		String url = String.format("%s/q001.htm", this.hnaPayService.getPayBaseUrl());
		QueryAccountRequest request = new QueryAccountRequest();
		request.setUserId(userId);
		request.setMerOrderId(merOrderId);
		request.setUrl(url);
		return this.request(hnaPayService, request, QueryAccountResult.class);
	}
}
