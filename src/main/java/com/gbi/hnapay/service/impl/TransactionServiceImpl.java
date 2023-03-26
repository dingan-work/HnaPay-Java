package com.gbi.hnapay.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gbi.hnapay.bean.request.enums.CashWithdrawalBusinessType;
import com.gbi.hnapay.bean.request.enums.PayType;
import com.gbi.hnapay.bean.request.transaction.CashWithdrawalRequest;
import com.gbi.hnapay.bean.request.transaction.ReceiptConfirmRequest;
import com.gbi.hnapay.bean.request.transaction.TransferRequest;
import com.gbi.hnapay.bean.request.transaction.pay.PlaceOrderPayRequest;
import com.gbi.hnapay.bean.request.transaction.pay.PlacePayConfirmRequest;
import com.gbi.hnapay.bean.result.enums.ResultCode;
import com.gbi.hnapay.bean.result.transaction.CashWithdrawalResult;
import com.gbi.hnapay.bean.result.transaction.TransferResult;
import com.gbi.hnapay.bean.result.transaction.pay.PlaceOrderPayResult;
import com.gbi.hnapay.bean.result.transaction.pay.PlacePayConfirmResult;
import com.gbi.hnapay.exception.HnaPayTransactionException;
import com.gbi.hnapay.service.HnaPayService;
import com.gbi.hnapay.service.TransactionService;
import com.gbi.hnapay.service.impl.base.BaseRequestImpl;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * @author ganhua
 */
@RequiredArgsConstructor
public class TransactionServiceImpl extends BaseRequestImpl implements TransactionService {

	private final HnaPayService hnaPayService;

	@Override
	public PlaceOrderPayResult quickPaymentOrder(PlaceOrderPayRequest request) {
		String url = String.format("%s/t007.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		request.setMerUserIp(hnaPayService.getConfig().getMerUserIp());
		request.setNotifyUrl(hnaPayService.getConfig().getQuickPayAsyncCallbackUrl());
		// 收款方ID为空时默认担保交易
		if (StrUtil.isBlank(request.getReceiveUserId())) {
			request.setReceiveUserId(hnaPayService.getConfig().getMerId());
		}
		request.setSubMerchantId(hnaPayService.getConfig().getSubMerchantId());
		if (StrUtil.isAllNotBlank(request.getUserId(), request.getBindCardAgrNo())) {
			request.setPayType(PayType.BANK_CARD_AGR_NO);
		} else {
			request.setPayType(PayType.BANK_CARD_NO);
		}
		PlaceOrderPayResult result = this.request(hnaPayService, request, PlaceOrderPayResult.class);
		if (result.isOk()) {
			if (!result.getSubmitTime().equals(request.getSubmitTime())) {
				throw new HnaPayTransactionException("提交时间不一致");
			}
		}
		return result;
	}

	@Override
	public PlacePayConfirmResult confirmPay(PlacePayConfirmRequest request) {
		String url = String.format("%s/t008.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		request.setMerUserIp(hnaPayService.getConfig().getMerUserIp());
		return this.request(hnaPayService, request, PlacePayConfirmResult.class);
	}

	@Override
	public Boolean confirmReceipt(ReceiptConfirmRequest request, String ncountOrderId) {
		String url = String.format("%s/f001.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(1);
		map.put("ncountOrderId", ncountOrderId);
		return this.requestBool(hnaPayService, request, map);
	}

	@Override
	public CashWithdrawalResult cashOut(CashWithdrawalRequest request) {
		String url = String.format("%s/t002.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		request.setNotifyUrl(hnaPayService.getConfig().getCashOutAsyncCallbackUrl());
		if (StrUtil.isNotBlank(request.getCardNo())) {
			request.setBusinessType(CashWithdrawalBusinessType.NON_BINDING_SAME_NAME);
		}
		return this.request(hnaPayService, request, CashWithdrawalResult.class);
	}

	@Override
	public TransferResult transfer(TransferRequest request) {
		String url = String.format("%s/t003.htm", this.hnaPayService.getPayBaseUrl());
		request.setUrl(url);
		return this.request(hnaPayService, request, TransferResult.class);
	}

	@Override
	public Boolean transfer(String payUserId, String receiveUserId, BigDecimal tranAmount) {
		TransferRequest request = new TransferRequest();
		request.setPayUserId(payUserId);
		request.setReceiveUserId(receiveUserId);
		request.setTranAmount(tranAmount);
		TransferResult transfer = transfer(request);
		return transfer.getResultCode() == ResultCode.SUCCESS;
	}
}
