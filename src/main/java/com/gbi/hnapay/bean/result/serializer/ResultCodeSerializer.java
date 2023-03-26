package com.gbi.hnapay.bean.result.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.gbi.hnapay.bean.result.enums.ResultEnumType;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author ganhua
 */
public class ResultCodeSerializer implements ObjectSerializer {
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
		ResultEnumType type = (ResultEnumType) object;
		serializer.out.writeString(type.getCode());
	}
}
