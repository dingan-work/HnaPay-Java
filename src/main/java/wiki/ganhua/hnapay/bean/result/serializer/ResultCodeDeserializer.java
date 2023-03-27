package wiki.ganhua.hnapay.bean.result.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import wiki.ganhua.hnapay.bean.result.enums.ResultEnumType;

import java.lang.reflect.Type;

/**
 * @author ganhua
 */
public class ResultCodeDeserializer implements ObjectDeserializer {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		// 解析值为字符串
		String value = parser.parseObject(String.class);
		Class<T> clazz = (Class<T>) type;
		T[] enumConstants = clazz.getEnumConstants();
		// 遍历所有的枚举实例
		for (ResultEnumType resultEnum : (ResultEnumType[]) enumConstants) {
			return (T)resultEnum.getEnumByCode(value);
		}
		// 没有匹配到，可以抛出异常或者返回null
		return null;
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_STRING;
	}
}
