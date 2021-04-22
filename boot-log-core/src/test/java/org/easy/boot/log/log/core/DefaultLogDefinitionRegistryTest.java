package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogDefinition;
import org.easy.boot.log.log.core.matedata.DefaultLogDefinition;
import org.easy.boot.log.log.core.model.User;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

/**
* 默认实现的日志定义加载器  
* @author : KangNing Hu
*/
public class DefaultLogDefinitionRegistryTest {


	/**
	 * 注册和获取测试
	 * @throws IntrospectionException
	 */
	@Test
	public void registerAndGet() throws IntrospectionException {

		BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
		MethodDescriptor methodDescriptor = beanInfo.getMethodDescriptors()[0];
		Method method = methodDescriptor.getMethod();

		DefaultLogDefinitionRegistry defaultLogDefinitionRegistry = new DefaultLogDefinitionRegistry();
		DefaultLogDefinition defaultLogDefinition = new DefaultLogDefinition();
		defaultLogDefinition.setMethod(method);
		defaultLogDefinition.setTargetClass(User.class);
		defaultLogDefinitionRegistry.register(defaultLogDefinition);
		LogDefinition logDefinition = defaultLogDefinitionRegistry.getLogDefinition( User.class , method);

		assert logDefinition == defaultLogDefinition : "注册和获取测试失败";
	}
}
