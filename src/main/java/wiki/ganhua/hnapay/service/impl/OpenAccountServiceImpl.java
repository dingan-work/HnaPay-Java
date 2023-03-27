package wiki.ganhua.hnapay.service.impl;

import wiki.ganhua.hnapay.bean.request.openaccount.IndividualOpenAccountRequest;
import wiki.ganhua.hnapay.bean.request.openaccount.QueryAccountRequest;
import wiki.ganhua.hnapay.bean.result.openaccount.IndividualResult;
import wiki.ganhua.hnapay.bean.result.openaccount.QueryAccountResult;
import wiki.ganhua.hnapay.service.HnaPayService;
import wiki.ganhua.hnapay.service.OpenAccountService;
import wiki.ganhua.hnapay.service.impl.base.BaseRequestImpl;
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
