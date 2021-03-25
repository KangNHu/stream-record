package org.dream.tool.auditlog.annotation;

import java.lang.annotation.*;

/**
* 参数标注器
* @author : KangNing Hu
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER , ElementType.FIELD})
@Documented
public @interface Param {
	/**
	 * 参数名称
	 * @return
	 */
	String value() default "";
}
