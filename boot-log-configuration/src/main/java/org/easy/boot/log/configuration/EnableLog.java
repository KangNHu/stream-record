package org.easy.boot.log.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* 审计日志激活  
* @author : KangNing Hu
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LogAutoConfiguration.class)
public @interface EnableLog {

}
