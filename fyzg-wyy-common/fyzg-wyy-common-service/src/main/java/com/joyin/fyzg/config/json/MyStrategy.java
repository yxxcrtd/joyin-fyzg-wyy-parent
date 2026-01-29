package com.joyin.fyzg.config.json;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * MyStrategy
 * <br/>
 *
 * @author pengzhen
 * @date 2019/8/9 0009 下午 1:56
 */
public class MyStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {

	@Override
	public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method,
			String defaultName) {
		if (method != null) {
			String propName = method.getName().substring(3);
			StringBuilder sb = new StringBuilder(propName);
			sb.setCharAt(0, Character.toLowerCase(propName.charAt(0)));
			return sb.toString();
		}
		else {
			return translate(defaultName);
		}
	}

	@Override
	public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName)
	{
		if (method != null) {
			String propName = method.getName().substring(3);
			StringBuilder sb = new StringBuilder(propName);
			sb.setCharAt(0, Character.toLowerCase(propName.charAt(0)));
			return sb.toString();
		}
		else {
			return translate(defaultName);
		}
	}


	@Override
	public String translate(String input) {
		if (input == null || input.length() == 0) {
			return input; // garbage in, garbage out
		}
		// Replace first lower-case letter with upper-case equivalent
		char c = input.charAt(0);
		char uc = Character.toLowerCase(c);
		if (c == uc) {
			return input;
		}
		StringBuilder sb = new StringBuilder(input);
		sb.setCharAt(0, uc);
		return sb.toString();
	}
}
