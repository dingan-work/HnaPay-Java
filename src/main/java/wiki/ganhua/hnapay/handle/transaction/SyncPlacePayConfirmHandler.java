package wiki.ganhua.hnapay.handle.transaction;

import com.alibaba.fastjson.JSON;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import wiki.ganhua.hnapay.bean.sync.transaction.pay.PlacePayConfirmSyncResult;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.handle.SyncTransactionHandler;
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
