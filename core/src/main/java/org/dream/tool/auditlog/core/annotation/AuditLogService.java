package org.dream.tool.auditlog.core.annotation;


import org.dream.tool.auditlog.core.matedata.Advice;
import org.dream.tool.auditlog.core.processor.Processor;
import org.dream.tool.auditlog.core.LogProducer;
import org.dream.tool.auditlog.core.Pipeline;
import org.dream.tool.auditlog.core.processor.ProcessorStrategy;

import java.lang.annotation.*;

/**
 * 审计 service 标识
 * @author hukangning
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE , ElementType.ANNOTATION_TYPE})
@Documented
public @interface AuditLogService {

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
	Class<? extends LogProducer> producerClass() default AuditLog.Void.class;


	/**
	 * 管道参数
	 * @return
	 */
	Class<? extends Pipeline> pipelineClass() default AuditLog.Void.class;
}
