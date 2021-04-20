package org.dream.tool.auditlog.core.utils;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
*   
* @author : KangNing Hu
*/
public class ProxyUtils {


	/**
	 * 获取 目标对象
	 * @param proxy 代理对象
	 * @return
	 * @throws Exception
	 */
	public static Object getTarget(Object proxy) {
		try {
			if(!AopUtils.isAopProxy(proxy)) {
				return proxy;//不是代理对象
			}

			if(AopUtils.isJdkDynamicProxy(proxy)) {
				return getJdkDynamicProxyTargetObject(proxy);
			} else { //cglib
				return getCglibProxyTargetObject(proxy);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 获取cglib的代理类
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		h.setAccessible(true);
		Object dynamicAdvisedInterceptor = h.get(proxy);

		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);

		return ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
	}


	/**
	 * 获取jdk代理的目标类
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(proxy);

		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);

		return ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
	}
}
