package com.gbi.hnapay.config;

import com.gbi.hnapay.bean.result.enums.BankCodeProperties;
import com.gbi.hnapay.bean.result.enums.ErrorCodeProperties;

/**
 * 新生支付配置策略.
 *
 * @author ganhua
 */
public class HnaPayConfigHolder {

	private static final ThreadLocal<String> THREAD_LOCAL = ThreadLocal.withInitial(() -> "default");

	private static BankCodeProperties BANK_CODE_PROPERTIES;

	private static ErrorCodeProperties ERROR_CODE_PROPERTIES;

	/**
	 * 获取当前新生支付配置策略.
	 *
	 * @return 当前新生支付配置策略
	 */
	public static String get() {
		return THREAD_LOCAL.get();
	}

	/**
	 * 设置当前新生支付配置策略.
	 *
	 * @param label 策略名称
	 */
	public static void set(final String label) {
		THREAD_LOCAL.set(label);
	}

	/**
	 * 此方法需要用户根据自己程序代码，在适当位置手动触发调用，本SDK里无法判断调用时机.
	 */
	public static void remove() {
		THREAD_LOCAL.remove();
	}

	public static void set(final BankCodeProperties bankCodeProperties) {
		BANK_CODE_PROPERTIES = bankCodeProperties;
	}

	public static void set(final ErrorCodeProperties errorCodeProperties) {
		ERROR_CODE_PROPERTIES = errorCodeProperties;
	}

	public static BankCodeProperties getBankCodeProperties() {
		return BANK_CODE_PROPERTIES;
	}

	public static ErrorCodeProperties getErrorCodeProperties() {
		return ERROR_CODE_PROPERTIES;
	}


}
