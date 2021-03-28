package org.dream.tool.auditlog.annotation;

import org.dream.tool.auditlog.matedata.Advice;
import org.dream.tool.auditlog.matedata.AuditLogInfo;
import org.dream.tool.auditlog.CurrentContext;
import org.dream.tool.auditlog.LogProducer;
import org.dream.tool.auditlog.Pipeline;
import org.dream.tool.auditlog.matedata.AuditLogInfoWrapper;
import org.dream.tool.auditlog.processor.ProcessorStrategy;

import java.lang.annotation.*;
import java.util.List;

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
