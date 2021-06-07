package org.codingeasy.streamrecord.core.matedata;

import org.codingeasy.streamrecord.core.processor.Processor;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 记录定义 用于说明 method 的记录记录
 * @author hukangning
 */
public interface RecordDefinition {


	Class<?> getTargetClass();

	/**
	 * 被记录的方法
	 * @return
	 */
	Method getMethod();


	/**
	 * 记录模版
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
	 * @see  Processor
	 * @return
	 */
	 String getProcessorStrategy();


	/**
	 * 记录生成器
	 * @return
	 */
	Class<? extends RecordProducer> getRecordProducerClass();


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
	 * 获取记录执行通知
	 * @return
	 */
	Advice getAdvice();
}
