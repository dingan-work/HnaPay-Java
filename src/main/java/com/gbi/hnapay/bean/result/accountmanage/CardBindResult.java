package com.gbi.hnapay.bean.result.accountmanage;

import com.gbi.hnapay.bean.result.BaseResult;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 绑卡接口同步返回
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CardBindResult extends BaseResult {

	/**
	 * 签约订单号
	 */
	private String ncountOrderId;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(1);
		map.put("ncountOrderId", this.ncountOrderId);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
