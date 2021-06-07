package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordDefinitionBuilder;
import org.codingeasy.streamrecord.core.model.Company;
import org.codingeasy.streamrecord.core.model.User;
import org.codingeasy.streamrecord.core.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
*   
* @author : KangNing Hu
*/
public class DefaultParamParseTest {



	class TestReflectiveMethodInvocation extends ReflectiveMethodInvocation{



		public TestReflectiveMethodInvocation(Object target, Method method, Object[] arguments, Class<?> targetClass){
			super(null , target , method , arguments , targetClass , null);
		}
	}

	/**
	 * 测试参数解析器
	 */
	@Test
	public void parse(){
		Method method = ReflectionUtils.findMethod(UserServiceImpl.class, "simple" , User.class);
		DefaultParamParse defaultParamParse = new DefaultParamParse();
		RecordDefinitionBuilder recordDefinitionBuilder = new RecordDefinitionBuilder(method, UserServiceImpl.class);
		DefaultRecordDefinitionRegistry defaultLogDefinitionRegistry = new DefaultRecordDefinitionRegistry();
		defaultLogDefinitionRegistry.register(recordDefinitionBuilder.getGenericRecordDefinition());
		AttributeAccess attributeAccess = new AttributeAccess();
		UserServiceImpl userService = new UserServiceImpl();
		User user = new User();
		user.setName("小王");
		Company company = new Company();
		company.setName("北京公司");
		user.setCompany(company);
		ReflectiveMethodInvocation reflectiveMethodInvocation = new TestReflectiveMethodInvocation( userService ,method ,new Object[]{user} ,
				UserServiceImpl.class );
		CurrentContext currentContext = new CurrentContext(defaultLogDefinitionRegistry , attributeAccess, new InterceptMethodWrapper(reflectiveMethodInvocation));
		currentContext.parseRecordDefinition(clazz -> BeanUtils.instantiateClass(clazz));
		defaultParamParse.parse(currentContext);
		assert "小王".equals(attributeAccess.get("name")) :"参数解析器测试失败";
		assert "北京公司".equals(attributeAccess.getAttribute("companyName")) :"参数解析器测试失败";
	}
}
