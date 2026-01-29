package com.joyin.fyzg.utils;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2020/12/18 0018 下午 5:37
 */
public class ForEachUtils {
	/**
	 * @param <T>
	 * @param startIndex 开始遍历的索引
	 * @param elements   集合
	 * @param action
	 */
	public static <T> void forEach(int startIndex, Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
		Objects.requireNonNull(elements);
		Objects.requireNonNull(action);
		if (startIndex < 0) {
			startIndex = 0;
		}
		int index = 0;
		for (T element : elements) {
			index++;
			if (index <= startIndex) {
				continue;
			}

			action.accept(index - 1, element);
		}
	}

	/**
	 * @param <T>
	 * @param elements 集合
	 * @param action
	 */
	public static <T> void forEach(Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
		Objects.requireNonNull(elements);
		Objects.requireNonNull(action);
		int index = 0;
		for (T element : elements) {
			action.accept(index++, element);
		}
	}
}
