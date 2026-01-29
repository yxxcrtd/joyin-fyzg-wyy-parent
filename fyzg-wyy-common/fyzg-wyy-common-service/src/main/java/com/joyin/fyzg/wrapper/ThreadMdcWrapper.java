package com.joyin.fyzg.wrapper;

import com.joyin.fyzg.utils.ThreadMdcUtil;
import org.slf4j.MDC;

public class ThreadMdcWrapper extends Thread{

    public ThreadMdcWrapper(Runnable target) {
        super(ThreadMdcUtil.wrap(target, MDC.getCopyOfContextMap()));
    }

}
