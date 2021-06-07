package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordDefinition;
import org.codingeasy.streamrecord.core.matedata.DefaultRecordDefinition;
import org.codingeasy.streamrecord.core.model.User;
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
public class DefaultRecordDefinitionRegistryTest {


	/**
	 * 注册和获取测试
	 * @throws IntrospectionException
	 */
	@Test
	public void registerAndGet() throws IntrospectionException {

		BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
		MethodDescriptor methodDescriptor = beanInfo.getMethodDescriptors()[0];
		Method method = methodDescriptor.getMethod();

		DefaultRecordDefinitionRegistry defaultLogDefinitionRegistry = new DefaultRecordDefinitionRegistry();
		DefaultRecordDefinition defaultLogDefinition = new DefaultRecordDefinition();
		defaultLogDefinition.setMethod(method);
		defaultLogDefinition.setTargetClass(User.class);
		defaultLogDefinitionRegistry.register(defaultLogDefinition);
		RecordDefinition recordDefinition = defaultLogDefinitionRegistry.getRecordDefinition( User.class , method);

		assert recordDefinition == defaultLogDefinition : "注册和获取测试失败";
	}
}
