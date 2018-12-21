package com.sf.marathon.np.util;

import java.io.StringWriter;
import java.io.Writer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	private JacksonUtil() {
		throw new IllegalAccessError();
	}

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public static String beanToJson(Object obj) {
		try {
			Writer write = new StringWriter();
			mapper.writeValue(write, obj);
			return write.toString();
		} catch (Exception e) {
			throw new RuntimeException("bean to json error!", e);
		}
	}

	public static <T> T jsonToBean(String jsonStr, Class<T> classType) {
		try {
			return mapper.readValue(jsonStr.getBytes("utf-8"), classType);
		} catch (Exception e) {
			throw new RuntimeException("json to bean error!", e);
		}
	}

}
