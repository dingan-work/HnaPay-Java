package com.gbi.hnapay.bean.request.transaction;

import com.gbi.hnapay.bean.request.BaseHnaPayRequest;
import com.gbi.hnapay.bean.result.enums.TranCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 线下入金确认
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OfflineTradeConfirmRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = 8016553832006291923L;

	/**
	 * 充值金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 银行编码 固定 PBOC
	 */
	private String bankCode = "PBOC";

	/**
	 * 币种 暂且支持人民币 1：人民币
	 */
	private String currencyType = "1";

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 充值商户新账通商户ID 默认是商户账户ID
	 */
	private String receiveUserId;

	/**
	 * 用户浏览器IP
	 */
	private String merUserIp;

	/**
	 * 异步通知地址
	 */
	private String notifyUrl;

	/**
	 * 业务类型 （默认支持 01：充值 ）
	 */
	private String businessType = "01";

	@Override
	public void encryptBody(String publicKey) {
		super.encryptBody(this, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.T004.name());
	}
}
