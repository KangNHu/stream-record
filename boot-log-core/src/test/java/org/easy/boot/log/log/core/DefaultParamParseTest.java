package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogDefinitionBuilder;
import org.easy.boot.log.log.core.model.Company;
import org.easy.boot.log.log.core.model.User;
import org.easy.boot.log.log.core.service.impl.UserServiceImpl;
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
		LogDefinitionBuilder logDefinitionBuilder = new LogDefinitionBuilder(method, UserServiceImpl.class);
		DefaultLogDefinitionRegistry defaultLogDefinitionRegistry = new DefaultLogDefinitionRegistry();
		defaultLogDefinitionRegistry.register(logDefinitionBuilder.getGenericLogDefinition());
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
		currentContext.parseLogDefinition(clazz -> BeanUtils.instantiateClass(clazz));
		defaultParamParse.parse(currentContext);
		assert "小王".equals(attributeAccess.get("name")) :"参数解析器测试失败";
		assert "北京公司".equals(attributeAccess.getAttribute("companyName")) :"参数解析器测试失败";
	}
}
