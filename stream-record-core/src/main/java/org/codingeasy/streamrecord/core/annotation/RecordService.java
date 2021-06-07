package org.codingeasy.streamrecord.core.annotation;


import org.codingeasy.streamrecord.core.matedata.Advice;
import org.codingeasy.streamrecord.core.processor.Processor;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;

import java.lang.annotation.*;

/**
 * 审计 service 标识
 * @author hukangning
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE , ElementType.ANNOTATION_TYPE})
@Documented
public @interface RecordService {

	/**
	 * 是否异步处理
	 * @return
	 */
	boolean isAsync() default true;


	/**
	 * 拦截方式
	 * @return
	 */
	Advice advice() default Advice.AFTER;


	/**
	 * 处理策略
	 * @see  ProcessorStrategy
	 * @see  Processor
	 * @return
	 */
	String strategy() default ProcessorStrategy.EXPRESSION_NAME;


	/**
	 * 记录生成器
	 * @return
	 */
	Class<? extends RecordProducer> producerClass() default Record.Void.class;


	/**
	 * 管道参数
	 * @return
	 */
	Class<? extends Pipeline> pipelineClass() default Record.Void.class;
}
