package wiki.ganhua.hnapay.bean.request.openaccount;

import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import lombok.*;


/**
 * 个人开户请求
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class IndividualOpenAccountRequest extends BaseHnaPayRequest {
	private static final long serialVersionUID = -4136980938640646686L;

	/**
	 * 商户用户唯一标识
	 */
	private String merUserId;

	/**
	 * 用户手机号
	 */
	private String mobile;

	/**
	 * 真实姓名
	 */
	private String userName;

	/**
	 * 身份证号 必须18位
	 */
	private String certNo;

	@Override
	public void encryptBody(String publicKey) {
		super.encryptBody(this, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.R010.name());
	}
}
