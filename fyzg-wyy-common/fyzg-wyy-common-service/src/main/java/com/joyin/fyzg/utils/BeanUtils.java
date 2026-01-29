package com.joyin.fyzg.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BeanUtils
 * <br/>
 *
 * @author pengzhen
 * @date 2020/3/26 0026 下午 5:37
 */
@Slf4j
public class BeanUtils {
	/**
	 * @param orig 源对象
	 * @param dest 目标对象
	 */
	public static void copyProperties(final Object orig, final Object dest) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @param origs           源list对象
	 * @param dests           目标list对象
	 * @param origsElementTpe 源list元素类型对象
	 * @param destElementTpe  目标list元素类型对象
	 * @param <T1>            源list元素类型
	 * @param <T2>            目标list元素类型
	 * @Description：拷贝list元素对象，将origs中的元素信息，拷贝覆盖至dests中
	 */
	public static <T1, T2> void copyProperties(final List<T1> origs, final List<T2> dests, Class<T1> origsElementTpe, Class<T2> destElementTpe) {
		if (origs == null || dests == null) {
			return;
		}
		if (dests.size() != 0) {
			//防止目标对象被覆盖，要求必须长度为零
			throw new RuntimeException("目标对象存在值");
		}
		try {
			for (T1 orig : origs) {
				T2 t = destElementTpe.newInstance();
				dests.add(t);
				copyProperties(orig, t);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	//		原文链接：https://blog.csdn.net/qq_31748587/article/details/85089626

	/**
	 * source 转 target
	 *
	 * @param source
	 * @param targetCls
	 * @param <TARGET>
	 * @return
	 */
	public static <TARGET> TARGET adapter(Object source, Class<TARGET> targetCls) {
		if (source == null) {
			return null;
		} else {
			TARGET entity;
			try {
				entity = targetCls.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("构造失败:", e);
				return null;
			}
			org.springframework.beans.BeanUtils.copyProperties(source, entity);
			return entity;
		}
	}

	/**
	 * source List 转 target List
	 */
	public static <R> List<R> copyBean(List<?> sourceList, Class<R> target) {
		return sourceList.stream().map(source -> adapter(source, target)).collect(Collectors.toList());
	}
}
