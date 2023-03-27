package wiki.ganhua.hnapay.bean.request.accountmanage;

import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 个人用户解绑接口请求
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CardUnbindingRequest extends BaseHnaPayRequest {

	/**
	 * 原绑卡协议号
	 */
	private String oriBindCardAgrNo;

	/**
	 * 用户ID
	 */
	private String userId;

	private static final long serialVersionUID = -1128625776657430123L;

	@Override
	public void encryptBody(String publicKey) {
		super.encryptBody(this, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.R009.name());
	}
}
