package org.codingeasy.streamrecord.core.annotation;

import org.codingeasy.streamrecord.core.matedata.Advice;
import org.codingeasy.streamrecord.core.processor.Processor;
import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Record {


	/**
	 * 记录表达式
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
	 * 记录生成器
	 * @return
	 */
	Class<? extends RecordProducer> producerClass() default Void.class;


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
	class Void implements Pipeline, RecordProducer {


		@Override
		public RecordInfoWrapper doProduce(CurrentContext currentContext) {
			return null;
		}

		@Override
		public void doConsume(RecordInfoWrapper recordInfoWrapper) {

		}
	}

}
