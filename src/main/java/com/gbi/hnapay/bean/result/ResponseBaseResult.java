package com.gbi.hnapay.bean.result;

import java.nio.charset.StandardCharsets;

/**
 * @author ganhua
 */
public class ResponseBaseResult extends BaseResult {

	@Override
	public byte[] signContent() {
		return basePlatformSignData(null).getBytes(StandardCharsets.UTF_8);
	}
}
