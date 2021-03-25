package org.dream.tool.auditlog;

import org.dream.tool.auditlog.processor.ProcessorStrategy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 日志定义 用于说明 method 的日志记录
 * @author hukangning
 */
public interface LogDefinition {

	/**
	 * 被记录的方法
	 * @return
	 */
	Method getMethod();


	/**
	 * 日志模版
	 * @return
	 */
	String getTemple();


	/**
	 * 是否异步处理
	 * @return
	 */
	Boolean isAsync();


	/**
	 * 处理策略
	 * @see  ProcessorStrategy
	 * @see  org.dream.tool.auditlog.processor.Processor
	 * @return
	 */
	 String getProcessorStrategy();


	/**
	 * 日志生成器
	 * @return
	 */
	Class<? extends LogProducer> getLogProducerClass();


	/**
	 * 管道参数
	 * @return
	 */
	Class<? extends Pipeline> getPipelineClass();

	/**
	 * 获取定义名称
	 * @return
	 */
	String getName();


	/**
	 * 获取参数
	 * @return
	 */
	List<ParamMetadata>[] getParams();


	/**
	 * 获取日志执行时机
	 * @return
	 */
	Advice getAdvice();
}
