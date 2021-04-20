package org.dream.tool.auditlog.core.annotation;

import org.dream.tool.auditlog.core.matedata.Advice;
import org.dream.tool.auditlog.core.processor.Processor;
import org.dream.tool.auditlog.core.CurrentContext;
import org.dream.tool.auditlog.core.LogProducer;
import org.dream.tool.auditlog.core.Pipeline;
import org.dream.tool.auditlog.core.matedata.AuditLogInfoWrapper;
import org.dream.tool.auditlog.core.processor.ProcessorStrategy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AuditLog {


	/**
	 * 日志表达式
	 * 当 strategy 为 ProcessorStrategy.EXPRESSION_NAME策略时 value 为 表达式
	 * 当 strategy 为 ProcessorStrategy.ROUTE 策略时 value 为路由的目标方法
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
	 * @see  Processor
	 * @return
	 */
	String strategy() default ProcessorStrategy.EXPRESSION_NAME;

	/**
	 * 日志生成器
	 * @return
	 */
	Class<? extends LogProducer> producerClass() default Void.class;


	/**
	 * 管道
	 * @return
	 */
	Class<? extends Pipeline> pipelineClass() default Void.class;


	/**
	 * 拦截方式
	 * @return
	 */
	Advice advice() default Advice.AFTER;

	/**
	 * 空的实现
	 */
	class Void implements Pipeline, LogProducer {


		@Override
		public AuditLogInfoWrapper doProduce(CurrentContext currentContext) {
			return null;
		}

		@Override
		public void doConsume(AuditLogInfoWrapper auditLogInfoWrapper) {

		}
	}

}
