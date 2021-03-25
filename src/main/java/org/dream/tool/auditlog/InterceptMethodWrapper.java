package org.dream.tool.auditlog;

import org.aopalliance.intercept.MethodInvocation;
import org.dream.tool.auditlog.utils.ProxyUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
* 拦截方法包装类
* @author : KangNing Hu
*/
public class InterceptMethodWrapper extends ArrayList<Object> {

	private Object proxy;

	private Method method;

	private MethodInvocation methodInvocation;

	public InterceptMethodWrapper(MethodInvocation methodInvocation){
		this.methodInvocation = methodInvocation;
		this.method = methodInvocation.getMethod();
		this.proxy = methodInvocation.getThis();
		this.addAll(Arrays.asList(methodInvocation.getArguments()));
	}


	/**
	 * 获取目标对象
	 * @return
	 */
	public Object getTarget(){
		return ProxyUtils.getTarget(this.proxy);
	}

	/**
	 * 获取方法
	 * @return
	 */
	public Method getMethod(){
		return this.method;
	}


	public MethodInvocation getMethodInvocation() {
		return methodInvocation;
	}
}