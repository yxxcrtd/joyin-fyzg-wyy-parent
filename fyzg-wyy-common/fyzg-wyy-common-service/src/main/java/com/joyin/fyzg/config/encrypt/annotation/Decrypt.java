package com.joyin.fyzg.config.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 解密注解
 * 
 * <p>加了此注解的接口将进行数据解密操作<p>
 *
 * @author yinjihuan http://cxytiandi.com/about
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {

	String value() default "";

	/**
	 * Url参数解密，多个参数用因为逗号分隔，比如 name,age
	 * @return 解密参数信息
	 */
	String decyptParam() default "";
	
}
