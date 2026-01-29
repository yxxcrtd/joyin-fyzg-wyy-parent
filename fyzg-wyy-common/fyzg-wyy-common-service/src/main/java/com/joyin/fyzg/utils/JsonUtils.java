package com.joyin.fyzg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyin.fyzg.common.exception.CommonException;
import com.joyin.fyzg.config.json.MyStrategy;
import com.joyin.fyzg.enums.CommonExceptionEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils(JackSon工具类)
 * <br/>
 *
 * @author pengzhen
 * @date 2019/8/19 0019 上午 9:50
 */
public class JsonUtils {

	private static ObjectMapper objectMapper = null;

	private JsonUtils() {

	}

	static  {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		objectMapper.setPropertyNamingStrategy(new MyStrategy());
	}

	public static ObjectMapper getInstance() {
		return objectMapper;
	}

	/**
	 * javaBean,list,array convert to json string
	 */
	public static String obj2json(Object obj) {
		String result = null;
		try {
			result = getInstance().writeValueAsString(obj);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CommonException(CommonExceptionEnum.JSON_OBJ2JSON.format(obj.toString(), e), e);
		}
		return result;
	}

	/**
	 * json string convert to javaBean
	 */
	public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
		T t = null;
		try {
			t = getInstance().readValue(jsonStr, clazz);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(CommonExceptionEnum.JSON_JSON2POJO.format(jsonStr, e), e);
		}
		return t;
	}

	/**
	 * json string convert to map
	 */
	public static Map<String, Object> json2map(String jsonStr) {
		Map map = null;
		try {
			map = getInstance().readValue(jsonStr, Map.class);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(CommonExceptionEnum.JSON_JSON2MAP.format(jsonStr, e), e);
		}
		return map;
	}

	/**
	 * json string convert to map with javaBean
	 */
	public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {
		Map<String, T> result = new HashMap<String, T>();
		Map<String, Map<String, Object>> map = null;
		try {
			map = getInstance().readValue(jsonStr,
					new TypeReference<Map<String, T>>() {
					});
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(CommonExceptionEnum.JSON_JSON2MAP.format(jsonStr, e), e);
		}
		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
			result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
		}
		return result;
	}

	/**
	 * json array string convert to list with javaBean
	 */
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		try {
			JavaType javaType = getInstance().getTypeFactory().constructParametricType(List.class, clazz);
			result = getInstance().readValue(jsonArrayStr, javaType);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(CommonExceptionEnum.JSON_JSON2LIST.format(jsonArrayStr, e), e);
		}
		return result;
	}

	/**
	 * map convert to javaBean
	 */
	public static <T> T map2pojo(Map map, Class<T> clazz) {
		return getInstance().convertValue(map, clazz);
	}

}

