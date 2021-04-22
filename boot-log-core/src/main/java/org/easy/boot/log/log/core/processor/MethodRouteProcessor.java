package org.easy.boot.log.log.core.processor;

import org.easy.boot.log.log.core.AttributeAccess;
import org.easy.boot.log.log.core.CurrentContext;
import org.easy.boot.log.log.core.InterceptMethodWrapper;
import org.easy.boot.log.log.core.matedata.LogInfo;
import org.easy.boot.log.log.core.matedata.DefaultLogDefinition;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
* 基于方法路由的处理策略
 * 路由规则
 * <code>
 *     // 业务类
 *     @AuditLogService
 *     public class A{
 *
 *          @AuditLog(value = "log" , strategy = ProcessorStrategy.ROUTE)
 *          @RouteTarget(AAuditLog.class)
 *          public void login(String value){
 *
 *          }
 *
 *          public void login(String value , String password)
 *     }
 *
 *     // 日志处理的目标类
 *     public class AAuditLog{
 *
 *         public void log(String value){
 *             ///自定义日志处理
 *         }
 *     }
 * </code>
 * 路由的目标处理方法的入参必须和业务方法一致，方法名称必须与@AuditLog#value一致
 * 可以额外的添加日志上下文中内建的对象
 * 1. InterceptMethodWrapper
 * 2. ParamAttribute
 * 这些参数可以通过以下方式进行添加
 * <code>
 *      // 业务类
 *       @AuditLogService
 *       public class A{
 *
 *            @AuditLog(value = "log" , strategy = ProcessorStrategy.ROUTE)
 *            @RouteTarget(AAuditLog.class , paramTypes = {InterceptMethodWrapper.class})
 *            public void login(String value){
 *
 *            }
 *
 *            public void login(String value , String password)
 *       }
 *
 *       // 日志处理的目标类
 *       public class AAuditLog{
 *
 *           public void log(String value ,InterceptMethodWrapper  interceptMethodWrapper){
 *               ///自定义日志处理
 *           }
 *       }
 * </code>
 * 内建的对象通过追加的方式添加到原方法入参中，如果路由的目标方法AAuditLog#log 在定义时没有声明这些参数，那么会抛出throws
 * {@link java.lang.reflect.InvocationTargetException}
* @author : KangNing Hu
*/
public class MethodRouteProcessor implements Processor {



	@Override
	public void process(CurrentContext currentContext) {
		//获取路由的方法
		DefaultLogDefinition logDefinition = (DefaultLogDefinition) currentContext.getLogDefinition();
		//进行调用
		invokeRouteMethod(logDefinition , currentContext);
	}


	/**
	 * 路由目标方法调用
	 * @param logDefinition 日志定义
	 * @param currentContext 当前执行上下文
	 */
	private void invokeRouteMethod(DefaultLogDefinition logDefinition , CurrentContext currentContext) {
		InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
		Method routeMethod = (Method)logDefinition.get(DefaultLogDefinition.ROUTE_METHOD);
		Class[] paramTypes = (Class[])logDefinition.get(DefaultLogDefinition.ROUTE_METHOD_PARAM_TYPES);
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
		//如何返回的值不为空者作为日志信息存储
		if (value == null){
			AttributeAccess attributeAccess = currentContext.getAttributeAccess();
			attributeAccess.put(LogInfo.MASSAGE_ATTR , value.toString());
		}
	}


}