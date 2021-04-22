package org.easy.boot.log.log.core.matedata;

import org.easy.boot.log.log.core.InterceptMethodWrapper;
import org.easy.boot.log.log.core.annotation.Log;
import org.easy.boot.log.log.core.annotation.Param;
import org.easy.boot.log.log.core.annotation.RouteTarget;
import org.easy.boot.log.log.core.annotation.Search;
import org.easy.boot.log.log.core.model.Company;
import org.easy.boot.log.log.core.model.User;
import org.easy.boot.log.log.core.processor.ProcessorStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
*   日志构建器单元测试类
 *   @see LogDefinitionBuilder
* @author : KangNing Hu
*/
public class LogDefinitionBuilderTest {


	private static Class aClass;


	private static Method elMethod;


	private static Method routeMethod;


	private static Method routeTargetMethod;
	/**
	 * 用于测试模型
	 * 测试 el表达式处理模型
	 * @param name
	 * @param userId
	 * @param user
	 * @param company
	 */
	@Log("测试日志")
	public void login(@Param String name , @Param("id") String userId , @Search User user , Company company){

	}

	/**
	 * 用户测试模型
	 * 测试 方法路由处理模型
	 * @param name
	 */
	@RouteTarget(value = LogDefinitionBuilderTest.class , paramTypes = {InterceptMethodWrapper.class})
	@Log(value = "log" ,strategy = ProcessorStrategy.ROUTE_NAME)
	public void login(@Param String name){

	}

	/**
	 * 用户测试模型
	 * 测试 方法路由处理模型
	 * @param name
	 * @param interceptMethodWrapper
	 */
	@RouteTarget(LogDefinitionBuilderTest.class)
	public void log(String name , InterceptMethodWrapper interceptMethodWrapper){

	}



	@BeforeClass
	public static void initClass() throws NoSuchMethodException {
		aClass = LogDefinitionBuilderTest.class;
		elMethod = aClass.getDeclaredMethod("login" , String.class , String.class , User.class , Company.class);
		routeMethod = aClass.getDeclaredMethod("login" , String.class);
		routeTargetMethod = aClass.getDeclaredMethod("log" ,String.class , InterceptMethodWrapper.class);
	}


	/**
	 * 获取一般bean定义构建
	 * el模型
	 */
	@Test
	public void getGenericLogDefinitionEl(){
		LogDefinitionBuilder logDefinitionBuilder = new LogDefinitionBuilder(elMethod, aClass);
		LogDefinition genericLogDefinition = logDefinitionBuilder.getGenericLogDefinition();
		//断言基本信息
		assertLogDefinitionBaseInfo(genericLogDefinition , elMethod , ProcessorStrategy.EXPRESSION_NAME , "测试日志");
		//断言参数信息
		List<ParamNode>[] params = genericLogDefinition.getParams();
		assert  params[0].size() == 1 : "第一个参数信息解析错误";
		assert  params[1].size() == 1 : "第二个参数信息解析错误";
		assert  params[2].size() == 2 : "第三个参数信息解析错误";
		assert  params[3].size() == 0 : "第四个参数解析错误";
	}


	/**
	 * 获取一般bean定义构建
	 * route method 模型
	 */
	@Test
	public void getGenericLogDefinitionRouteMethod(){
		LogDefinitionBuilder logDefinitionBuilder = new LogDefinitionBuilder(routeMethod, aClass);
		DefaultLogDefinition genericLogDefinition = (DefaultLogDefinition) logDefinitionBuilder.getGenericLogDefinition();
		//断言基本信息
		assertLogDefinitionBaseInfo(genericLogDefinition , routeMethod , ProcessorStrategy.ROUTE_NAME , "log");
		//断言参数信息
		List<ParamNode>[] params = genericLogDefinition.getParams();
		assert params[0].size() == 1 : "第一个参数信息解析错误";
		//断言 路由方法信息
		assert ((Method)genericLogDefinition.getAttribute(DefaultLogDefinition.ROUTE_METHOD)).toGenericString().equals(routeTargetMethod.toGenericString()): "路由方法解析错误";
		assert Arrays.asList((Class[])genericLogDefinition.get(DefaultLogDefinition.ROUTE_METHOD_PARAM_TYPES))
				.stream().map(Class::getTypeName).collect(Collectors.joining(",")).equals("java.lang.String,InterceptMethodWrapper"):
				"路由方法参数解析错误";
	}

	/**
	 * 断言log定义的基本信息
	 * @param genericLogDefinition
	 * @param method
	 * @param processorStrategy
	 */
	private void assertLogDefinitionBaseInfo(LogDefinition genericLogDefinition  , Method method , String processorStrategy , String temple){
		assert genericLogDefinition.isAsync() : "异步方式解析错误";
		assert genericLogDefinition.getLogProducerClass().isAssignableFrom(Log.Void.class) : "日志生成器解析错误";
		assert genericLogDefinition.getAdvice() == Advice.AFTER : "拦截方式解析错误";
		assert genericLogDefinition.getMethod() == method : "目标方法解析错误";
		assert genericLogDefinition.getPipelineClass().isAssignableFrom(Log.Void.class) : "管道解析错误";
		assert genericLogDefinition.getProcessorStrategy().equals(processorStrategy) : "处理策略解析错误";
		assert genericLogDefinition.getTemple().equals(temple) :"日志表达式解析错误";
		assert genericLogDefinition.getName().equals("") : "定义名称解析错误";
		assert genericLogDefinition.getTargetClass().isAssignableFrom(LogDefinitionBuilderTest.class) : "目标参数解析错误";

	}
}
