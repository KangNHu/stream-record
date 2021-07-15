package org.codingeasy.streamrecord.core.matedata;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.streamrecord.core.AttributeAccess;
import org.codingeasy.streamrecord.core.InterceptMethodWrapper;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.annotation.*;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;
import org.codingeasy.streamrecord.core.utils.MethodUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
*   记录定义构建器
* @author : KangNing Hu
*/
public class RecordDefinitionBuilder {

	//路由目标
	public final static String ROUTE_TARGET = RecordDefinitionBuilder.class.getName() + "_route_target";

	//路由方法对象
	public final static String ROUTE_METHOD = RecordDefinitionBuilder.class.getName() + "_route_method";
	//路由方法参数列表
	public final static String ROUTE_METHOD_PARAM_TYPES =RecordDefinitionBuilder.class.getName() + "_route_method_param_types";
	//方法对象
	private Method method;

	//方法所在类对象
	private Class clazz;

	public RecordDefinitionBuilder(Method method , Class clazz){
		assert method != null : "构建记录定义的 method 不能为空";
		assert clazz != null  : "构建记录定义的 class 不能为空";
		this.method = method;
		this.clazz = clazz;
	}

	/**
	 * 获取一般的record定义
	 * @return
	 */
	public RecordDefinition getGenericRecordDefinition(){
		//获取类的全局元信息
		RecordService recordService = AnnotationUtils.findAnnotation(clazz, RecordService.class);
		assert recordService != null : String.format("%s 类缺少必要@RecordService" , this.clazz.toGenericString());
		Advice advice = recordService.advice();
		Class<? extends Pipeline> pipelineClass = recordService.pipelineClass();
		Class<? extends RecordProducer> recordProducerClass = recordService.producerClass();
		String strategy = recordService.strategy();
		//获取基本元信息
		Record record = AnnotationUtils.findAnnotation(method, Record.class);
		assert  record != null : String.format("%s 方法缺少必要的@Record" , method.toGenericString());
		DefaultRecordDefinition defaultRecordDefinition = new DefaultRecordDefinition();
		defaultRecordDefinition.setAdvice(advice == Advice.NONE ? record.advice() : advice);
		defaultRecordDefinition.setAsync(record.isAsync());
		defaultRecordDefinition.setRecordProducerClass(recordProducerClass == Record.Void.class ? record.producerClass() : recordProducerClass);
		defaultRecordDefinition.setMethod(method);
		defaultRecordDefinition.setName("");
		defaultRecordDefinition.setPipelineClass(pipelineClass == Record.Void.class? record.pipelineClass() : pipelineClass);
		defaultRecordDefinition.setProcessorStrategy(StringUtils.isEmpty(strategy)? record.strategy() : strategy);
		defaultRecordDefinition.setTargetClass(clazz);
		defaultRecordDefinition.setTemple(record.value());
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
		defaultRecordDefinition.setParams(params);
		expandProcessor(defaultRecordDefinition);
		return defaultRecordDefinition;
	}

	/**
	 * 扩展处理
	 * 处理 方法路由模型
	 * @param defaultRecordDefinition record 定义基本信息
	 */
	private void expandProcessor(DefaultRecordDefinition defaultRecordDefinition) {
		String strategy = defaultRecordDefinition.getProcessorStrategy();
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
						break;
					}
				}
				/**
				 * 只指定内建参数类型  如：
				 * {@link InterceptMethodWrapper} {@link AttributeAccess}
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
			Method method = ReflectionUtils.findMethod(targetClass, defaultRecordDefinition.getTemple(), paramTypes);
			//如果找不到则是一个无效的路由目标
			if (method == null){
				throw new IllegalArgumentException(String.format("%s在%s中是一个无效的路由目标方法" ,
						Arrays.asList(paramTypes).stream().map(Class::getTypeName).collect(Collectors.joining()) , targetClass.getTypeName()));
			}
			defaultRecordDefinition.setAttribute(ROUTE_TARGET ,targetClass);
			defaultRecordDefinition.setAttribute(ROUTE_METHOD , method);
			defaultRecordDefinition.setAttribute(ROUTE_METHOD_PARAM_TYPES , paramTypes);
		}
	}


}
