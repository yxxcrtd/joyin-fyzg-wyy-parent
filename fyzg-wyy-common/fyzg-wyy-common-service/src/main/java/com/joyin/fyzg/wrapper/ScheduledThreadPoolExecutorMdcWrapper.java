package com.joyin.fyzg.wrapper;

import com.joyin.fyzg.utils.ThreadMdcUtil;
import org.slf4j.MDC;

import java.util.concurrent.*;

public class ScheduledThreadPoolExecutorMdcWrapper extends ScheduledThreadPoolExecutor {
    public ScheduledThreadPoolExecutorMdcWrapper(int corePoolSize) {
        super(corePoolSize);
    }

    public ScheduledThreadPoolExecutorMdcWrapper(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public ScheduledThreadPoolExecutorMdcWrapper(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public ScheduledThreadPoolExecutorMdcWrapper(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
