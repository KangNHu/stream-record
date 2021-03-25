package org.dream.tool.auditlog.annotation;

import org.dream.tool.auditlog.LogProducer;
import org.dream.tool.auditlog.Pipeline;
import org.dream.tool.auditlog.processor.ProcessorStrategy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AuditLog {


	/**
	 * 日志模版
	 * @return
	 */
	String value();


	/**
	 * 是否异步处理
	 * @return
	 */
	boolean isAsync() default true;


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
	Class<? extends LogProducer> producerClass() default Void.class;


	/**
	 * 管道参数
	 * @return
	 */
	Class<? extends Pipeline> pipelineClass() default Void.class;


	/**
	 * 空的实现
	 */
	class Void implements Pipeline, LogProducer {

		@Override
		public Object doProduce() {
			return null;
		}

		@Override
		public void doConsume(Object log) {

		}
	}

}
