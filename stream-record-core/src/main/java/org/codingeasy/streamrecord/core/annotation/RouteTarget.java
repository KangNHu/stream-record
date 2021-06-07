package org.codingeasy.streamrecord.core.annotation;

import org.codingeasy.streamrecord.core.AttributeAccess;
import org.codingeasy.streamrecord.core.InterceptMethodWrapper;
import org.codingeasy.streamrecord.core.processor.MethodRouteProcessor;

import java.lang.annotation.*;


/**
 * 指定路由的目标class
 * @author hukangning
 * @see MethodRouteProcessor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RouteTarget {

	/**
	 * 目标类
	 *
	 * @return
	 */
	Class<?> value();


	/**
	 * 目标方法参数列表
	 * 1.默认为空列表 ，则取业务方法的参数列表
	 * 2.只指定内建参数类型  如：
	 * {@link InterceptMethodWrapper} {@link AttributeAccess}
	 * 那么将这些参数追加到原有的参数列表后面
	 * 3.指定 完整的参数列表
	 * 2，3 根据 是否有内建参数类型之外的类型进行区分
	 * @return
	 */
	Class<?>[] paramTypes() default {};
}

