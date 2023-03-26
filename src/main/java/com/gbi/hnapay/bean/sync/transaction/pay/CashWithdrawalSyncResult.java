package com.gbi.hnapay.bean.sync.transaction.pay;

import com.alibaba.fastjson.annotation.JSONField;
import com.gbi.hnapay.bean.result.BaseResult;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CashWithdrawalSyncResult extends BaseResult {

	/**
	 * 订单号
	 */
	private String ncountOrderId;

	@JSONField(format = "yyyyMMdd")
	private Date orderDate;

	@JSONField(format = "yyyyMMdd")
	private Date tranFinishDate;

	/**
	 * 服务费
	 */
	private BigDecimal serviceAmount;

	/**
	 * 付款方账户余额
	 */
	private BigDecimal payAcctAmount;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(1);
		map.put("ncountOrderId", this.ncountOrderId);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
