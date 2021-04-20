package org.dream.tool.auditlog.core.annotation;

import java.lang.annotation.*;

/**
* 参数标注器
 * 和{@link Param} 只有一个生效 其中 {@link Param} > {@link Search}
 * @see Search
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
