package org.dream.tool.auditlog.matedata;

import org.dream.tool.auditlog.LogProducer;
import org.dream.tool.auditlog.Pipeline;
import org.dream.tool.auditlog.processor.ProcessorStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 日志定义 用于说明 method 的日志记录
 * @author hukangning
 */
public interface LogDefinition {


	Class<?> getTargetClass();

	/**
	 * 被记录的方法
	 * @return
	 */
	Method getMethod();


	/**
	 * 日志模版
	 * 当 {@link this#getProcessorStrategy()}为{@link ProcessorStrategy#EXPRESSION_NAME}时 则代表的是el表达式模版
	 * 当 {@link this#getProcessorStrategy()}为{@link ProcessorStrategy#ROUTE_NAME}时 则代表路由的目标方法名称
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
	 * 获取参数列表元数据
	 * @return
	 */
	List<ParamNode>[] getParams();


	/**
	 * 获取日志执行时机
	 * @return
	 */
	Advice getAdvice();
}
