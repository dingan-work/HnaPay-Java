package wiki.ganhua.hnapay.service.impl.base;

import com.alibaba.fastjson.JSON;
import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import wiki.ganhua.hnapay.bean.result.ResponseBaseResult;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.exception.HnaPayTransactionException;
import wiki.ganhua.hnapay.exception.HnaPayVerifySignFailException;
import wiki.ganhua.hnapay.service.BaseRequestService;
import wiki.ganhua.hnapay.service.HnaPayService;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 请求封装基础类
 *
 * @author ganhua
 */
@Slf4j
public class BaseRequestImpl implements BaseRequestService {

	@Override
	public <T> T request(HnaPayService service, BaseHnaPayRequest request, Class<T> tClass) {
		HnaPayConfig config = service.getConfig();
		T result = JSON.parseObject(service.post(request.getUrl(), request.formParams(config)), tClass);
		BaseResult baseResult = (BaseResult) result;
		if (baseResult.isOk()) {
			if (baseResult.verifySign(baseResult.signContent(), config.getPlatformPublicKey(), config.getRsaAlgorithm().name())) {
				return result;
			}
			throw new HnaPayVerifySignFailException(baseResult.signContent(), baseResult.getSignValue());
		}
		throw new HnaPayTransactionException(baseResult.getErrorCode(), baseResult.getErrorMsg());
	}

	@Override
	public boolean requestBool(HnaPayService service, BaseHnaPayRequest request) {
		return this.requestBool(service, request, null);
	}

	@Override
	public boolean requestBool(HnaPayService service, BaseHnaPayRequest request, Map<String, Object> additionalSignData) {
		HnaPayConfig config = service.getConfig();
		ResponseBaseResult result = JSON.parseObject(service.post(request.getUrl(), request.formParams(config)), ResponseBaseResult.class);
		if (result.isOk()) {
			byte[] bytesSign = result.basePlatformSignData(additionalSignData).getBytes(StandardCharsets.UTF_8);
			if (result.verifySign(bytesSign, config.getPlatformPublicKey(), config.getRsaAlgorithm().name())) {
				return true;
			}
			throw new HnaPayVerifySignFailException(bytesSign, result.getSignValue());
		}
		throw new HnaPayTransactionException(result.getErrorCode(), result.getErrorMsg());
	}
}
