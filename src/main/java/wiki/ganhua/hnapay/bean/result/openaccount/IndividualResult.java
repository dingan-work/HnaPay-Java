package wiki.ganhua.hnapay.bean.result.openaccount;

import wiki.ganhua.hnapay.bean.result.BaseResult;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 个人开户新账通平台同步返回
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class IndividualResult extends BaseResult {
	/**
	 * 用户编号
	 */
	private String userId;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(1);
		map.put("userId", this.userId);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
