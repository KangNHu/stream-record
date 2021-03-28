package org.dream.tool.auditlog.matedata;

import org.apache.commons.lang3.ArrayUtils;
import org.dream.tool.auditlog.AttributeAccess;
import org.dream.tool.auditlog.InterceptMethodWrapper;
import org.dream.tool.auditlog.LogProducer;
import org.dream.tool.auditlog.Pipeline;
import org.dream.tool.auditlog.annotation.*;
import org.dream.tool.auditlog.processor.ProcessorStrategy;
import org.dream.tool.auditlog.utils.MethodUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
*   日志定义构建器
* @author : KangNing Hu
*/
public class LogDefinitionBuilder {

	//方法对象
	private Method method;

	//方法所在类对象
	private Class clazz;

	public LogDefinitionBuilder(Method method , Class clazz){
		this.method = method;
		this.clazz = clazz;
	}

	/**
	 * 获取一般的log定义
	 * @return
	 */
	public LogDefinition getGenericLogDefinition(){
		//获取全局元信息
		AuditLogService auditLogService = AnnotationUtils.findAnnotation(clazz, AuditLogService.class);
		Boolean async = null;
		Advice advice = null;
		Class<? extends Pipeline> pipelineClass = null;
		Class<? extends LogProducer> logProducerClass = null;
		String strategy = null;
		if (auditLogService != null) {
			async = auditLogService.isAsync();
			advice = auditLogService.advice();
			pipelineClass = auditLogService.pipelineClass();
			logProducerClass = auditLogService.producerClass();
			strategy = auditLogService.strategy();
		}
		//获取基本元信息
		AuditLog auditLog = AnnotationUtils.findAnnotation(method, AuditLog.class);
		assert  auditLog != null : "方法缺少必要的@AuditLog";
		DefaultLogDefinition defaultLogDefinition = new DefaultLogDefinition();
		defaultLogDefinition.setAdvice(advice == null ? auditLog.advice() : advice);
		defaultLogDefinition.setAsync(async == null ? auditLog.isAsync() : async);
		defaultLogDefinition.setLogProducerClass(logProducerClass == null ? auditLog.producerClass() : logProducerClass);
		defaultLogDefinition.setMethod(method);
		defaultLogDefinition.setName("");
		defaultLogDefinition.setPipelineClass(pipelineClass == null? auditLog.pipelineClass() : pipelineClass);
		defaultLogDefinition.setProcessorStrategy(strategy == null ? auditLog.strategy() : strategy);
		defaultLogDefinition.setTargetClass(clazz);
		defaultLogDefinition.setTemple(auditLog.value());
		//解析参数
		String[] paramNames = MethodUtils.getParamNames(this.method);
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		int parameterLength = parameterTypes.length;
		List<ParamNode>[] params = new List[parameterLength];
		for (int i = 0 ; i < parameterLength ; i ++){
			Param param = MethodUtils.findAnnotationByMethodParam(parameterAnnotations, i, Param.class);
			Search search = MethodUtils.findAnnotationByMethodParam(parameterAnnotations, i, Search.class);
			Class<?> parameterType = parameterTypes[i];
			if (param != null){
				params[i] = Arrays.asList(new ParamNode(param.value() == null ? paramNames[i] : param.value()));
			}else if (search != null){
				List<ParamNode> paramNodes = new ArrayList<>();
				do {
					ReflectionUtils.doWithFields(parameterType , field -> {
						paramNodes.add(ParamNode.build(field));
					} ,field -> field.isAnnotationPresent(Param.class) || field.isAnnotationPresent(Search.class));
					parameterType = parameterType.getSuperclass().isAssignableFrom(Object.class) ? null : parameterType.getSuperclass();
				}while (parameterType != null);
				params[i] = paramNodes;
			}else {
				params[i] = Collections.EMPTY_LIST;
			}
		}
		defaultLogDefinition.setParams(params);
		expandProcessor(defaultLogDefinition);
		return defaultLogDefinition;
	}

	/**
	 * 扩展处理
	 * 处理 方法路由模型
	 * @param defaultLogDefinition log 定义基本信息
	 */
	private void expandProcessor(DefaultLogDefinition defaultLogDefinition) {
		String strategy = defaultLogDefinition.getProcessorStrategy();
		if (ProcessorStrategy.ROUTE_NAME.equals(strategy)){
			RouteTarget routeTarget = AnnotationUtils.getAnnotation(this.method, RouteTarget.class);
			Class<?> targetClass = routeTarget.value();
			Class<?>[] parameterTypes = this.method.getParameterTypes();
			Class<?>[] classes = routeTarget.paramTypes();
			Class<?>[] paramTypes = null;
			//默认为空列表 ，则取业务方法的参数列表
			if (ArrayUtils.isEmpty(classes)){
				paramTypes = parameterTypes;
			}
			else {
				//指定 完整的参数列表
				for (Class<?> paramType : classes) {
					if (!(paramType.isAssignableFrom(InterceptMethodWrapper.class)
							|| paramType.isAssignableFrom(AttributeAccess.class))) {
						paramTypes = classes;
					}
				}
				/**
				 * 只指定内建参数类型  如：
				 * {@link org.dream.tool.auditlog.InterceptMethodWrapper} {@link org.dream.tool.auditlog.AttributeAccess}
				 * 那么将这些参数追加到原有的参数列表后面
				 */
				if (paramTypes == null){
					paramTypes = new Class[parameterTypes.length + classes.length];
					for (int i = 0 , length = paramTypes.length ; i < length ; i ++){
						if (i < parameterTypes.length){
							paramTypes[i] = parameterTypes[i];
						}else {
							paramTypes[i] = classes[i - parameterTypes.length];
						}
					}
				}
			}
			Method method = ReflectionUtils.findMethod(targetClass, defaultLogDefinition.getTemple(), paramTypes);
			//如果找不到则是一个无效的路由目标
			if (method == null){
				throw new IllegalArgumentException(String.format("%s在%s中是一个无效的路由目标方法" ,
						Arrays.asList(paramTypes).stream().map(Class::getTypeName).collect(Collectors.joining()) , targetClass.getTypeName()));
			}
			defaultLogDefinition.put(DefaultLogDefinition.ROUTE_METHOD , method);
			defaultLogDefinition.put(DefaultLogDefinition.ROUTE_METHOD_PARAM_TYPES , paramTypes);
		}
	}


}
