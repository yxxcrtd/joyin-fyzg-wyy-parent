package com.joyin.fyzg.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * 日志切面
 * <br/>
 * @author pengzhen
 * @date 2019/7/23 0023 下午 12:01
 */
@Aspect
@Component
public class LogAspect {
	private static Logger controllerLog = LoggerFactory.getLogger("controllerLog");
	private static Logger serviceLog = LoggerFactory.getLogger("serviceLog");

	@Pointcut("execution(public * com.joyin.fyzg.*.controller..*.*(..))")
	public void controllerLog() {

	}

	/**
	 * controller方法入口记录入参到controller的日志中
	 * <br/>
	 *
	 * @param joinPoint
	 * @return void
	 * @author pengzhen
	 * @date 2019/7/16 0016 下午 4:07
	 */
	@Before("controllerLog()")
	public void doBeforeController(JoinPoint joinPoint) {
		logParam(controllerLog, joinPoint);
	}

	/**
	 * controller方法入口记录返回值到controller的日志中
	 * <br/>
	 *
	 * @param joinPoint
	 * @param ret
	 * @return void
	 * @author pengzhen
	 * @date 2019/7/16 0016 下午 4:07
	 */
	@AfterReturning(returning = "ret", pointcut = "controllerLog()")
	public void doAfterReturningController(JoinPoint joinPoint, Object ret) throws Throwable {
		logReturn(controllerLog, joinPoint, ret);
	}

	@Pointcut("execution(public * com.joyin.fyzg.*.service..*.*(..))")
	public void serviceLog() {

	}

	/**
	 * service方法入口记录入参到service的日志中
	 * <br/>
	 *
	 * @param joinPoint
	 * @return void
	 * @author pengzhen
	 * @date 2019/7/16 0016 下午 4:06
	 */
	@Before("serviceLog()")
	public void doBeforeService(JoinPoint joinPoint) {
		logParam(serviceLog, joinPoint);
	}

	/**
	 * service方法入口记录返回值到service的日志中
	 * <br/>
	 *
	 * @param joinPoint
	 * @param ret
	 * @return void
	 * @author pengzhen
	 * @date 2019/7/16 0016 下午 4:07
	 */
	@AfterReturning(returning = "ret", pointcut = "serviceLog()")
	public void doAfterReturningService(JoinPoint joinPoint, Object ret) throws Throwable {
		logReturn(serviceLog, joinPoint, ret);
	}

	/**
	 * 记录方法带入的参数
	 * <br/>
	 *
	 * @param logger
	 * @param joinPoint
	 * @return void
	 * @author pengzhen
	 * @date 2019/7/16 0016 下午 4:05
	 */
	private void logParam(Logger logger, JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容config
		String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
		paramNames = Optional.ofNullable(paramNames).orElse(new String[]{});
		Object[] paramValues = joinPoint.getArgs();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < paramNames.length; i++) {
			sb.append(paramNames[i]).append(":").append(paramValues[i]).append(",");
		}
		StringBuffer res = new StringBuffer();
		res.append("class:[").append(joinPoint.getSignature().getDeclaringTypeName()).append("]method:[")
				.append(joinPoint.getSignature().getName())
				.append("]param:[")
				.append(sb)
				.append("]");
		logger.info(res.toString());
	}

	/**
	 * 记录方法的返回值
	 * <br/>
	 * @param logger
	 * @param joinPoint
	 * @param ret
	 * @return void
	 * @author pengzhen
	 * @date 2019/7/16 0016 下午 4:06
	 */
	private void logReturn(Logger logger, JoinPoint joinPoint, Object ret) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
		StringBuffer res = new StringBuffer();
		res.append("class:[").append(joinPoint.getSignature().getDeclaringTypeName()).append("]method:[").append(joinPoint.getSignature().getName()).append("]");
		logger.info(res.toString());
	}
}
