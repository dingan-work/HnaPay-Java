package com.gbi.hnapay.bean.result;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.gbi.hnapay.bean.result.enums.ResultCode;
import com.gbi.hnapay.bean.result.serializer.ResultCodeDeserializer;
import com.gbi.hnapay.bean.result.serializer.ResultCodeSerializer;
import com.gbi.hnapay.config.HnaPayConfig;
import com.gbi.hnapay.config.HnaPayConfigHolder;
import com.gbi.hnapay.util.HnaPayRsaUtil;
import lombok.Data;

import java.util.Map;

/**
 * 通用返回数据
 *
 * @author ganhua
 */
@Data
public abstract class BaseResult {

	/**
	 * 版本号 目前必须为1.0
	 */
	private String version;

	/**
	 * 交易代码
	 */
	private String tranCode;

	/**
	 * 商户ID
	 * 新账通平台提供给商户 的唯一 ID
	 */
	private String merId;

	/**
	 * 商户订单号
	 * 格式：数字，字母，下 划线，竖划线，中划线
	 */
	private String merOrderId;


	/**
	 * 签名类型 1: RSA
	 */
	private String signType;

	/**
	 * 签名密文串
	 */
	private String signValue;

	/**
	 * 附加数据 长度限制0-80
	 */
	private String merAttach;

	/**
	 * 编码方式 1: UTF8
	 */
	private String charset;

	/**
	 * 处理结果码
	 */
	@JSONField(serializeUsing = ResultCodeSerializer.class, deserializeUsing = ResultCodeDeserializer.class)
	private ResultCode resultCode;

	/**
	 * 异常代码
	 */
	private String errorCode;

	/**
	 * 异常描述
	 */
	private String errorMsg;

	public boolean isOk() {
		return this.resultCode == ResultCode.SUCCESS || this.resultCode == ResultCode.IN_PROGRESS;
	}

	public String basePlatformSignData(Map<String, Object> params) {
		String str = "version=[%s]tranCode=[%s]merOrderId=[%s]merId=[%s]charset=[%s]signType=[%s]resultCode=[%s]errorCode=[%s]";
		StringBuilder baseData = new StringBuilder(String.format(str, this.version, this.tranCode, this.merOrderId, this.merId,
			this.charset, this.signType, this.resultCode.getCode(), this.errorCode == null ? "" : this.errorCode));
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> next : params.entrySet()) {
				baseData.append(next.getKey()).append("=[").append(next.getValue()).append("]");
			}
		}
		return baseData.toString();
	}

	/**
	 * 原签名内容
	 *
	 * @return 签名内容
	 */
	public abstract byte[] signContent();

	/**
	 * 验签是否通过
	 */
	public boolean verifySign(byte[] data, String platformPublicKey, String signAlgorithm) {
		return HnaPayRsaUtil.verify(data, platformPublicKey, this.signValue, signAlgorithm);
	}

	/**
	 * 验签是否通过
	 */
	public boolean verifySign(byte[] data, String platformPublicKey) {
		return HnaPayRsaUtil.verify(data, platformPublicKey, this.signValue, HnaPayConfig.SignatureAlgorithm.SHA1withRSA.name());
	}

	/**
	 * 根据错误代码获取错误详细信息
	 *
	 * @return 错误信息
	 */
	public String getErrorMsg() {
		if (StrUtil.isNotBlank(this.errorMsg)) {
			return this.errorMsg;
		}
		return HnaPayConfigHolder.getErrorCodeProperties().getDescribe(this.errorCode);
	}
}
