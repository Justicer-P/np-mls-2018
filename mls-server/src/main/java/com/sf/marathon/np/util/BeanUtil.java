package com.sf.marathon.np.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class BeanUtil {

	private BeanUtil() {

	}

	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public static <T> T copyProperties(Object source, Class<T> clazz, String... ignoreProperties) {
		T target = null;
		try {
			target = clazz.newInstance();
			BeanUtils.copyProperties(source, target, ignoreProperties);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.warn("warn", e);
		}
		return target;

	}
}
