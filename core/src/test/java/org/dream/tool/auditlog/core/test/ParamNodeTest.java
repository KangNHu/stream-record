package org.dream.tool.auditlog.core.test;

import org.dream.tool.auditlog.core.annotation.AuditLog;
import org.dream.tool.auditlog.core.annotation.Search;
import org.dream.tool.auditlog.core.matedata.LogDefinition;
import org.dream.tool.auditlog.core.matedata.LogDefinitionBuilder;
import org.dream.tool.auditlog.core.matedata.ParamNode;
import org.dream.tool.auditlog.core.test.model.Address;
import org.dream.tool.auditlog.core.test.model.Company;
import org.dream.tool.auditlog.core.test.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 参数节点测试类
 * @see ParamNode
* @author : KangNing Hu
*/
public class ParamNodeTest {

	private User user;



	@AuditLog(value = "测试日志")
	public void log(@Search User user){

	}

	@Before
	public void init(){
		user = new User();
		user.setId("u123456");
		user.setName("小网");
		Company company = new Company();
		company.setId("c123456");
		company.setName("测试公司");
		Address address = new Address();
		address.setAddr("广州");
		address.setAddressId("a123456");
		address.setCityCode("431001");
		company.setAddress(address);
		user.setCompany(company);
	}


	/**
	 * 测试方法参数解析
	 */
	@Test
	public void parseParams(){
		LogDefinitionBuilder logDefinitionBuilder = new LogDefinitionBuilder(ReflectionUtils.findMethod(ParamNodeTest.class, "log", User.class), ParamNodeTest.class);
		LogDefinition genericLogDefinition = logDefinitionBuilder.getGenericLogDefinition();
		List<ParamNode>[] params = genericLogDefinition.getParams();
		List<ParamNode> param = params[0];
		Map<String , Object> attr = new HashMap<>();
		for (ParamNode paramNode : param){
			attr.putAll(paramNode.parseParam(user));
		}
		String[] attrNames = {"name" , "companyName"  ,"addr" , "cityCode"};
		String[] values = {"小网" , "测试公司" , "广州" , "431001"};
		for (int i = 0 ; i < attrNames.length ; i ++){
			assert attr.get(attrNames[i]).equals(values[i]) : "参数解析错误";
		}

	}

}
