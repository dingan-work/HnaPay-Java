package com.gbi.hnapay.handle;


import cn.hutool.core.convert.Convert;
import com.gbi.hnapay.bean.result.BaseResult;
import com.gbi.hnapay.bean.result.enums.ResultCode;
import com.gbi.hnapay.config.HnaPayConfig;
import com.gbi.hnapay.exception.HnaPayVerifySignFailException;

import java.util.Map;

/**
 * 基于网络及安全的考虑，新账通平台会针对同一笔订单，发送多次通知。商户在接收到通知后，请务必判断同一订单号是否已经针对交易成功的情况进行过处理。
 * 如果订单已经按照支付成功的情况进行过处理，商户再次接收到通知时不应重复处理。
 * 因网络或目标资金机构服务器的原因，新账通平台第一次发送交易失败的结果给商户，后续可能会对同一订单号发送交易成功的通知给商户。
 * 商户应在对通知结果验签成功后，对相同订单号按照交易成功的业务逻辑进行处理。即商户收到的通知有失败也有成功时，以成功通知为准进行相关业务处理。
 * 异步通知地址，系统将结果报文以 POST 形式提交到该异步通知地址上，商户收到报文后进行相应的业务处理，处理完毕后将处理结果写入响应流中。
 * 商户需回送"200"以确认收到异步通知。
 *
 * @author ganhua
 */
public interface SyncTransactionHandler {

	/**
	 * 逻辑处理
	 *
	 * @param requestParams 请求参数
	 * @param tClass        返回类型
	 * @return 返回的数据请求
	 */
	default <T extends BaseResult> T handleLogic(Map<String, Object> requestParams, Class<T> tClass) {
		return null;
	}

	/**
	 * 对比数据签名
	 *
	 * @param result       返回数据
	 * @param tClass       数据类型
	 * @param hnaPayConfig config
	 * @return 签名成功后数据
	 */
	default <T extends BaseResult> T handleLogic(BaseResult result, Class<T> tClass, HnaPayConfig hnaPayConfig) {
		if (result.getResultCode() == ResultCode.SUCCESS) {
			if (result.verifySign(result.signContent(), hnaPayConfig.getPlatformPublicKey(), hnaPayConfig.getRsaAlgorithm().name())) {
				return Convert.convert(tClass, result);
			}
			throw new HnaPayVerifySignFailException(result.signContent(), result.getSignValue());
		}
		return Convert.convert(tClass, result);
	}

}
