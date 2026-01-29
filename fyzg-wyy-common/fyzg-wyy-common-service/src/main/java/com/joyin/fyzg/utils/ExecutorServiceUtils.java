package com.joyin.fyzg.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorServiceUtils
 * <br/>
 *
 * @author pengzhen
 * @date 2020/3/26 0026 下午 5:37
 */
@Slf4j
public class ExecutorServiceUtils {

	private final static long AWAIT_TIME = 500L;

	public static void shutdown(ExecutorService executorService) {
		try {
			// 通知池内线程关闭
			executorService.shutdown();

			// 通知shutdown还未关闭线程则等待超时后立即关闭线程
			if (!executorService.awaitTermination(AWAIT_TIME, TimeUnit.MILLISECONDS)) {
				// 立即关闭线程
				executorService.shutdownNow();
			}
		}
		catch (InterruptedException e) {
			// awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
			log.error("关闭线程遇到错误： " + e);
			executorService.shutdownNow();
		}
	}
}
