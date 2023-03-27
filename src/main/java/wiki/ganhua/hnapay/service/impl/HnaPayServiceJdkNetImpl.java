package wiki.ganhua.hnapay.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.service.impl.base.BaseHnaPayServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Okhttp实现
 *
 * @author ganhua
 */
@Slf4j
public class HnaPayServiceJdkNetImpl extends BaseHnaPayServiceImpl {

	@Override
	public String post(String url, Map<String, Object> data) {
		HnaPayConfig config = this.getConfig();
		HttpResponse response = HttpRequest.post(url)
			.setConnectionTimeout(config.getHttpConnectionTimeout())
			.setReadTimeout(config.getHttpTimeout()).form(data).execute();
		log.info("新生支付数据请求:{},返回数据:{}", JSON.toJSONString(data), response);
		return response.body();

	}
}
