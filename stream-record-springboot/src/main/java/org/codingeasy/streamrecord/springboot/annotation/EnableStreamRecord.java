package org.codingeasy.streamrecord.springboot.annotation;

import org.codingeasy.streamrecord.springboot.configuration.StreamRecordAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* 激活 流式记录
* @author : KangNing Hu
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(StreamRecordAutoConfiguration.class)
public @interface EnableStreamRecord {

}
