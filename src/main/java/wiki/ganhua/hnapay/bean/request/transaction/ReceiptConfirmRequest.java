package wiki.ganhua.hnapay.bean.request.transaction;

import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;

/**
 * 收货确认接口
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ReceiptConfirmRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = -5484446078867292978L;

	/**
	 * 原商户订单号
	 */
	private String orgMerOrderId;

	/**
	 * 分账主订单号
	 */
	private String divideId;

	/**
	 * 分账明细订单号
	 */
	private String divideDtlId;

	@Override
	public void encryptBody(String publicKey) {
		LinkedHashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(3);
		map.put("orgMerOrderId", this.orgMerOrderId);
		map.put("divideId", this.divideId);
		map.put("divideDtlId", this.divideDtlId);
		super.encryptBody(map, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.FOO1.name());
	}
}
