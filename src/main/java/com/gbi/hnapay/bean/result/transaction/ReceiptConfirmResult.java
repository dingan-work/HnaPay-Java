package com.gbi.hnapay.bean.result.transaction;

import com.gbi.hnapay.bean.result.BaseResult;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 确认收货返回
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ReceiptConfirmResult extends BaseResult {

	private String ncountOrderId;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(1);
		map.put("ncountOrderId", this.ncountOrderId);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
