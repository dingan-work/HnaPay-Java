package wiki.ganhua.hnapay.bean.request.openaccount;

import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询接口请求
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class QueryAccountRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = -8054127328646391153L;

	/**
	 * 用户编号
	 */
	private String userId;


	@Override
	public void encryptBody(String publicKey) {
		super.encryptBody(this, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.Q001.name());
	}
}
