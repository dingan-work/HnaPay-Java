package com.gbi.hnapay.bean.sync.transaction.pay;

import com.gbi.hnapay.bean.result.BaseResult;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 快捷支付确认接口新账通平台异步返回参数
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlacePayConfirmSyncResult extends BaseResult {

	/**
	 * 新账通订单号
	 */
	private String ncountOrderId;

	/**
	 * 商户订单金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 对账日期
	 */
	private String checkDate;

	/**
	 * 提交时间
	 */
	private String submitTime;

	/**
	 * 交易完成时间
	 */
	private String tranFinishTime;

	/**
	 * 签约银行简码
	 */
	private String bankCode;

	/**
	 * 支付银行卡卡类型
	 */
	private String cardType;

	/**
	 * 支付银行卡后四位
	 */
	private String shortCardNo;

	/**
	 * 绑卡协议号
	 */
	private String bindCardAgrNo;

	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 交易手续费
	 */
	private String feeAmount;

	/**
	 * 分账订单明细
	 */
	private String divideAcctDtl;

	/**
	 * 收款方账户余额
	 */
	private BigDecimal recvAcctAmount;

	/**
	 * 分期期数
	 */
	private Integer instalmentNum;

	/**
	 * 分期应付手续费
	 */
	private BigDecimal payableFeeAmt;

	/**
	 * 手续费支付方式
	 */
	private Integer payableFeeType;

	/**
	 * 首期手续费
	 */
	private BigDecimal firstPeriodFeeAmt;

	/**
	 * 每期手续费
	 */
	private BigDecimal eachPeriodFeeAmt;

	/**
	 * 首期还款金额
	 */
	private BigDecimal firstPeriodPayAmt;

	/**
	 * 商户分期实际贴息费率
	 */
	private String instalmentRate;

	/**
	 * 商户分期实际贴息费用
	 */
	private BigDecimal instalmentAmt;


	@Override
	public byte[] signContent() {
		HashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(9);
		map.put("ncountOrderId", this.ncountOrderId);
		map.put("tranAmount", this.tranAmount);
		map.put("checkDate", this.checkDate);
		map.put("submitTime", this.submitTime);
		map.put("tranFinishTime", this.tranFinishTime);
		map.put("bankCode", this.bankCode);
		map.put("cardType", this.cardType);
		map.put("shortCardNo", this.shortCardNo);
		map.put("bindCardAgrNo", this.bindCardAgrNo);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
