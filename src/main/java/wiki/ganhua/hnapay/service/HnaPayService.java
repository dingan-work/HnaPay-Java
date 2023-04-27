package wiki.ganhua.hnapay.service;

import wiki.ganhua.hnapay.bean.sync.transaction.pay.PlacePayConfirmSyncResult;
import wiki.ganhua.hnapay.config.HnaPayConfig;

import java.util.Map;

/**
 * 新生支付相关接口
 *
 * @author ganhua
 */
public interface HnaPayService {

	/**
	 * 获取配置.
	 *
	 * @return the config
	 */
	HnaPayConfig getConfig();


	/**
	 * 进行相应的商户切换.
	 *
	 * @param merId 商户标识
	 * @return 切换是否成功 boolean
	 */
	boolean switchover(String merId);

	/**
	 * 进行相应的商户切换.
	 *
	 * @param merId 商户标识
	 * @return 切换成功 ，则返回当前对象，方便链式调用，否则抛出异常
	 */
	HnaPayService switchoverTo(String merId);

	/**
	 * 设置配置对象.
	 *
	 * @param config the config
	 */
	void setConfig(HnaPayConfig config);

	/**
	 * Map里 加入新的 {@link HnaPayConfig}，适用于动态添加新的新生商户配置.
	 *
	 * @param mchId        商户id
	 * @param hnaPayConfig 新的新生支付配置
	 */
	void addConfig(String mchId, HnaPayConfig hnaPayConfig);

	/**
	 * 注入多个 {@link HnaPayConfig} 的实现. 并为每个 {@link HnaPayConfig} 赋予不同的 {@link String label} 值
	 *
	 * @param hnaPayConfigs HnaPayConfig map
	 * @param defaultMchId  设置一个{@link HnaPayConfig} 所对应的{@link String mchId}进行Http初始化
	 */
	void setMultiConfig(Map<String, HnaPayConfig> hnaPayConfigs, String defaultMchId);

	/**
	 * 获取新生支付请求url前缀
	 *
	 * @return the pay base url
	 */
	String getPayBaseUrl();

	/**
	 * 发送post请求，得到响应字符串
	 *
	 * @param url  请求url
	 * @param data 请求数据
	 * @return 返回的数据
	 */
	String post(String url, Map<String, Object> data);

	/**
	 * 开户相关服务类.
	 *
	 * @return the open account service
	 */
	OpenAccountService getOpenAccountService();

	/**
	 * 账户管理相关服务类
	 *
	 * @return the account manage service
	 */
	AccountManageService getAccountManageService();

	/**
	 * 交易相关服务类
	 *
	 * @return the transaction manage service
	 */
	TransactionService getTransactionService();

	/**
	 * 文件下载相关服务
	 * @return the file download manage service
	 */
	FileDownloadService getFileDownloadService();

	/**
	 * 解析快捷支付回调通知
	 *
	 * @param params 回调参数
	 * @return 回调通知
	 */
	PlacePayConfirmSyncResult parseQuickPayNotifyResult(Map<String, Object> params);

}
