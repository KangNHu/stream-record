package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.AttributeAccess;
import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.InterceptMethodWrapper;
import org.codingeasy.streamrecord.core.matedata.RecordInfo;
import org.codingeasy.streamrecord.core.matedata.DefaultRecordDefinition;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
* 基于方法路由的处理策略
* @author : KangNing Hu
*/
public class MethodRouteProcessor implements Processor {



	@Override
	public void process(CurrentContext currentContext) {
		//获取路由的方法
		DefaultRecordDefinition recordDefinition = (DefaultRecordDefinition) currentContext.getRecordDefinition();
		//进行调用
		invokeRouteMethod(recordDefinition , currentContext);
	}


	/**
	 * 路由目标方法调用
	 * @param recordDefinition 记录定义
	 * @param currentContext 当前执行上下文
	 */
	private void invokeRouteMethod(DefaultRecordDefinition recordDefinition , CurrentContext currentContext) {
		InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
		Method routeMethod = (Method)recordDefinition.get(DefaultRecordDefinition.ROUTE_METHOD);
		Class[] paramTypes = (Class[])recordDefinition.get(DefaultRecordDefinition.ROUTE_METHOD_PARAM_TYPES);
		int length = paramTypes.length;
		Object[] params = new Object[length];
		for (int i = 0 ; i < length ; i ++){
			Class<?> paramType = paramTypes[i];
			if (paramType.isAssignableFrom(InterceptMethodWrapper.class)){
				params[i] = currentContext.getInterceptMethodWrapper();
			}else if (paramType.isAssignableFrom(AttributeAccess.class)){
				params[i] = currentContext.getAttributeAccess();
			}else {
				params[i] = interceptMethodWrapper.get(i);
			}
		}
		Object value = ReflectionUtils.invokeMethod(routeMethod, params);
		//如何返回的值不为空者作为记录信息存储
		if (value == null){
			AttributeAccess attributeAccess = currentContext.getAttributeAccess();
			attributeAccess.put(RecordInfo.MASSAGE_ATTR , value.toString());
		}
	}


}