package wiki.ganhua.hnapay.bean.result.enums;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常代码
 *
 * @author ganhua
 */
@Configuration(proxyBeanMethods = false)
@PropertySource(value = "classpath:error/error_code.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "error")
@Getter
@Setter
public class ErrorCodeProperties {

	private Map<String, String> code = new HashMap<>();

	/**
	 * 获取描述信息
	 *
	 * @param code code码
	 * @return 描述信息
	 */
	public String getDescribe(String code) {
		return this.code.get(code);
	}
}
