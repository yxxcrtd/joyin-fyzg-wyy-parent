package com.joyin.fyzg.utils;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Iterable 的工具类
 * <br/>
 *
 * @author Administrator
 * @date 2020/8/16 0016 下午 5:16
 */
public class IterableUtils {

	public static <E> void forEach(
			Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
		Objects.requireNonNull(elements);
		Objects.requireNonNull(action);

		int index = 0;
		for (E element : elements) {
			action.accept(index++, element);
		}
	}
}
