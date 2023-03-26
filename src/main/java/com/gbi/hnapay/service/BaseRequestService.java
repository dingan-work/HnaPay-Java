package com.gbi.hnapay.service;

import com.gbi.hnapay.bean.request.BaseHnaPayRequest;

import java.util.Map;

/**
 * 基础请求接口
 *
 * @author ganhua
 */
public interface BaseRequestService {

	/**
	 * 请求数据 返回信息
	 *
	 * @param service 支付接口
	 * @param request 请求对象
	 * @param tClass  数据类型
	 * @return 数据对象
	 */
	<T> T request(HnaPayService service, BaseHnaPayRequest request, Class<T> tClass);

	/**
	 * 请求数据 是否请求成功
	 * @param service 支付接口
	 * @param request 请求对象
	 * @return 接口是否正常处理
	 */
	boolean requestBool(HnaPayService service, BaseHnaPayRequest request);

	/**
	 * 请求数据 是否请求成功
	 * @param service 支付接口
	 * @param request 请求对象
	 * @param additionalSignData 附加验签数据
	 * @return 接口是否正常处理
	 */
	boolean requestBool(HnaPayService service, BaseHnaPayRequest request, Map<String,Object> additionalSignData);

}
