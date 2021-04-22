package org.easy.boot.log.log.core.annotation;


import org.easy.boot.log.log.core.matedata.Advice;
import org.easy.boot.log.log.core.processor.Processor;
import org.easy.boot.log.log.core.LogProducer;
import org.easy.boot.log.log.core.Pipeline;
import org.easy.boot.log.log.core.processor.ProcessorStrategy;

import java.lang.annotation.*;

/**
 * 审计 service 标识
 * @author hukangning
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE , ElementType.ANNOTATION_TYPE})
@Documented
public @interface LogService {

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
	 * 日志生成器
	 * @return
	 */
	Class<? extends LogProducer> producerClass() default Log.Void.class;


	/**
	 * 管道参数
	 * @return
	 */
	Class<? extends Pipeline> pipelineClass() default Log.Void.class;
}
