package com.gbi.hnapay.service.impl.base;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.gbi.hnapay.bean.sync.transaction.pay.PlacePayConfirmSyncResult;
import com.gbi.hnapay.config.HnaPayConfig;
import com.gbi.hnapay.config.HnaPayConfigHolder;
import com.gbi.hnapay.exception.HnaPayException;
import com.gbi.hnapay.handle.SyncTransactionHandler;
import com.gbi.hnapay.handle.transaction.SyncPlacePayConfirmHandler;
import com.gbi.hnapay.service.AccountManageService;
import com.gbi.hnapay.service.HnaPayService;
import com.gbi.hnapay.service.OpenAccountService;
import com.gbi.hnapay.service.TransactionService;
import com.gbi.hnapay.service.impl.AccountManageServiceImpl;
import com.gbi.hnapay.service.impl.OpenAccountServiceImpl;
import com.gbi.hnapay.service.impl.TransactionServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 新生支付接口请求抽象实现类
 *
 * @author ganhua
 */
@Slf4j
public abstract class BaseHnaPayServiceImpl implements HnaPayService {

	@Getter
	private final OpenAccountService openAccountService = new OpenAccountServiceImpl(this);

	@Getter
	private final AccountManageService accountManageService = new AccountManageServiceImpl(this);

	@Getter
	private final TransactionService transactionService = new TransactionServiceImpl(this);

	protected ConcurrentHashMap<String, HnaPayConfig> configMap;

	@Override
	public HnaPayConfig getConfig() {
		if (this.configMap.size() == 1) {
			return this.configMap.values().iterator().next();
		}
		return this.configMap.get(HnaPayConfigHolder.get());
	}

	@Override
	public void setConfig(HnaPayConfig config) {
		final String defaultMchId = config.getMerId();
		this.setMultiConfig(MapUtil.of(defaultMchId, config), defaultMchId);
	}

	@Override
	public void addConfig(String merId, HnaPayConfig hnaPayConfig) {
		synchronized (this) {
			if (this.configMap == null) {
				this.setConfig(hnaPayConfig);
			} else {
				HnaPayConfigHolder.set(merId);
				this.configMap.put(merId, hnaPayConfig);
			}
		}
	}

	@Override
	public boolean switchover(String merId) {
		if (this.configMap.containsKey(merId)) {
			HnaPayConfigHolder.set(merId);
			return true;
		}
		log.error("无法找到对应【{}】的商户号配置信息，请核实！", merId);
		return false;
	}

	@Override
	public HnaPayService switchoverTo(String merId) {
		if (this.configMap.containsKey(merId)) {
			HnaPayConfigHolder.set(merId);
			return this;
		}
		throw new HnaPayException(String.format("无法找到对应【%s】的商户号配置信息，请核实！", merId));
	}

	@Override
	public void setMultiConfig(Map<String, HnaPayConfig> hnaPayConfigs, String defaultMchId) {
		this.configMap = MapUtil.newConcurrentHashMap(hnaPayConfigs);
		HnaPayConfigHolder.set(defaultMchId);
	}

	@Override
	public String getPayBaseUrl() {
		return StrUtil.removeSuffix(this.getConfig().getPayBaseUrl(), "/");
	}

	@Override
	public PlacePayConfirmSyncResult parseQuickPayNotifyResult(Map<String, Object> params) {
		SyncTransactionHandler handler = new SyncPlacePayConfirmHandler(this.getConfig());
		return handler.handleLogic(params, PlacePayConfirmSyncResult.class);
	}
}
