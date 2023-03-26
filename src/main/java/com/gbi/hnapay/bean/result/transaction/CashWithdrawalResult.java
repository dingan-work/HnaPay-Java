package com.gbi.hnapay.bean.result.transaction;

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
@EqualsAndHashCode(callSuper = false)
@Data
public class CashWithdrawalResult extends BaseResult {

	/**
	 * 订单号
	 */
	private String ncountOrderId;

	/**
	 * 平台订单时间
	 */
	@JSONField(format = "yyyyMMdd")
	private Date orderDate;

	/**
	 * 服务费
	 */
	private BigDecimal serviceAmount;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(1);
		map.put("ncountOrderId", this.ncountOrderId);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
