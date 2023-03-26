package com.gbi.hnapay.handle.transaction;

import com.alibaba.fastjson.JSON;
import com.gbi.hnapay.bean.result.BaseResult;
import com.gbi.hnapay.bean.sync.transaction.pay.CashWithdrawalSyncResult;
import com.gbi.hnapay.config.HnaPayConfig;
import com.gbi.hnapay.handle.SyncTransactionHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * 提现异步通知
 *
 * @author ganhua
 */
@RequiredArgsConstructor
public class SyncCashWithdrawalHandler implements SyncTransactionHandler {

	private final HnaPayConfig hnaPayConfig;

	@Override
	public <T extends BaseResult> T handleLogic(Map<String, Object> requestParams, Class<T> tClass) {
		CashWithdrawalSyncResult result = JSON.parseObject(JSON.toJSONString(requestParams), CashWithdrawalSyncResult.class);
		return SyncTransactionHandler.super.handleLogic(result, tClass, hnaPayConfig);
	}

}
