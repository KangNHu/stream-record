package org.easy.boot.log.configuration;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* 需要日志处理的service  
* @author : KangNing Hu
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface LogService {

	@AliasFor(annotation =Component.class , attribute = "value")
	String value() default "";
}
