package wiki.ganhua.hnapay.handle.transaction;

import com.alibaba.fastjson.JSON;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import wiki.ganhua.hnapay.bean.sync.transaction.pay.CashWithdrawalSyncResult;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.handle.SyncTransactionHandler;
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
