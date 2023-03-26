package com.gbi.hnapay.bean.result.transaction.pay;

import com.gbi.hnapay.bean.result.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PlaceOrderPayResult extends BaseResult {

	/**
	 * 新账通订单号
	 */
	private String ncountOrderId;

	/**
	 * 商户请求时间
	 */
	private String submitTime;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = new HashMap<>(2);
		map.put("ncountOrderId", this.ncountOrderId);
		map.put("submitTime", this.submitTime);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
