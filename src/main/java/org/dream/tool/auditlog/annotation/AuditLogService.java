package org.dream.tool.auditlog.annotation;


import org.dream.tool.auditlog.LogProducer;
import org.dream.tool.auditlog.Pipeline;
import org.dream.tool.auditlog.matedata.Advice;
import org.dream.tool.auditlog.processor.ProcessorStrategy;

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
	 * @see  org.dream.tool.auditlog.processor.Processor
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
