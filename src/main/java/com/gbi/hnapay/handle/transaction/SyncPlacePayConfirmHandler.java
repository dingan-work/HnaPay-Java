package com.gbi.hnapay.handle.transaction;

import com.alibaba.fastjson.JSON;
import com.gbi.hnapay.bean.result.BaseResult;
import com.gbi.hnapay.bean.sync.transaction.pay.PlacePayConfirmSyncResult;
import com.gbi.hnapay.config.HnaPayConfig;
import com.gbi.hnapay.handle.SyncTransactionHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * 快捷支付确认处理
 *
 * @author ganhua
 */
@RequiredArgsConstructor
public class SyncPlacePayConfirmHandler implements SyncTransactionHandler {

	private final HnaPayConfig hnaPayConfig;

	@Override
	public <T extends BaseResult> T handleLogic(Map<String, Object> requestParams, Class<T> tClass) {
		PlacePayConfirmSyncResult result = JSON.parseObject(JSON.toJSONString(requestParams), PlacePayConfirmSyncResult.class);
		return SyncTransactionHandler.super.handleLogic(result, tClass, hnaPayConfig);
	}
}
