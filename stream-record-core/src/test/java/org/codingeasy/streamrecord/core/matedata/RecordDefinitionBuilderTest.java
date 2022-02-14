package org.codingeasy.streamrecord.core.matedata;

import org.codingeasy.streamrecord.core.InterceptMethodWrapper;
import org.codingeasy.streamrecord.core.annotation.*;
import org.codingeasy.streamrecord.core.model.Company;
import org.codingeasy.streamrecord.core.model.User;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.codingeasy.streamrecord.core.matedata.RecordDefinitionBuilder.ROUTE_METHOD;
import static org.codingeasy.streamrecord.core.matedata.RecordDefinitionBuilder.ROUTE_METHOD_PARAM_TYPES;

/**
 * 日志构建器单元测试类
 *
 * @author : KangNing Hu
 * @see RecordDefinitionBuilder
 */
@RecordService
public class RecordDefinitionBuilderTest {


  private static Class aClass;


  private static Method elMethod;


  private static Method routeMethod;


  private static Method routeTargetMethod;

  /**
   * 用于测试模型 测试 el表达式处理模型
   */
  @Record("测试日志")
  public void login(@Param String name, @Param("id") String userId, @Search User user,
      Company company) {

  }

  /**
   * 用户测试模型 测试 方法路由处理模型
   */
  @RouteTarget(value = RecordDefinitionBuilderTest.class, paramTypes = {
      InterceptMethodWrapper.class})
  @Record(value = "log", strategy = ProcessorStrategy.ROUTE_NAME)
  public void login(@Param String name) {

  }

  /**
   * 用户测试模型 测试 方法路由处理模型
   */
  @RouteTarget(RecordDefinitionBuilderTest.class)
  public void log(String name, InterceptMethodWrapper interceptMethodWrapper) {

  }


  @BeforeClass
  public static void initClass() throws NoSuchMethodException {
    aClass = RecordDefinitionBuilderTest.class;
    elMethod = aClass
        .getDeclaredMethod("login", String.class, String.class, User.class, Company.class);
    routeMethod = aClass.getDeclaredMethod("login", String.class);
    routeTargetMethod = aClass.getDeclaredMethod("log", String.class, InterceptMethodWrapper.class);
  }


  /**
   * 获取一般bean定义构建 el模型
   */
  @Test
  public void getGenericLogDefinitionEl() {
    RecordDefinitionBuilder recordDefinitionBuilder = new RecordDefinitionBuilder(elMethod, aClass);
    RecordDefinition genericRecordDefinition = recordDefinitionBuilder.getGenericRecordDefinition();
    //断言基本信息
    assertLogDefinitionBaseInfo(genericRecordDefinition, elMethod,
        ProcessorStrategy.EXPRESSION_NAME, "测试日志");
    //断言参数信息
    List<ParamNode>[] params = genericRecordDefinition.getParams();
    assert params[0].size() == 1 : "第一个参数信息解析错误";
    assert params[1].size() == 1 : "第二个参数信息解析错误";
    assert params[2].size() == 2 : "第三个参数信息解析错误";
    assert params[3].size() == 0 : "第四个参数解析错误";
  }


  /**
   * 获取一般bean定义构建 route method 模型
   */
  @Test
  public void getGenericLogDefinitionRouteMethod() {
    RecordDefinitionBuilder recordDefinitionBuilder = new RecordDefinitionBuilder(routeMethod,
        aClass);
    DefaultRecordDefinition genericLogDefinition = (DefaultRecordDefinition) recordDefinitionBuilder
        .getGenericRecordDefinition();
    //断言基本信息
    assertLogDefinitionBaseInfo(genericLogDefinition, routeMethod, ProcessorStrategy.ROUTE_NAME,
        "log");
    //断言参数信息
    List<ParamNode>[] params = genericLogDefinition.getParams();
    assert params[0].size() == 1 : "第一个参数信息解析错误";
    //断言 路由方法信息
    assert ((Method) genericLogDefinition.getAttribute(ROUTE_METHOD)).toGenericString()
        .equals(routeTargetMethod.toGenericString()) : "路由方法解析错误";
    assert Arrays.asList((Class[]) genericLogDefinition.get(ROUTE_METHOD_PARAM_TYPES))
        .stream().map(Class::getTypeName).collect(Collectors.joining(","))
        .equals("java.lang.String,org.codingeasy.streamrecord.core.InterceptMethodWrapper") :
        "路由方法参数解析错误";
  }

  /**
   * 断言记录定义的基本信息
   */
  private void assertLogDefinitionBaseInfo(RecordDefinition genericRecordDefinition, Method method,
      String processorStrategy, String temple) {
    assert genericRecordDefinition.isAsync() : "异步方式解析错误";
    assert genericRecordDefinition.getRecordProducerClass()
        .isAssignableFrom(Record.Void.class) : "日志生成器解析错误";
    assert genericRecordDefinition.getAdvice() == Advice.AFTER : "拦截方式解析错误";
    assert genericRecordDefinition.getMethod() == method : "目标方法解析错误";
    assert genericRecordDefinition.getPipelineClass()
        .isAssignableFrom(Record.Void.class) : "管道解析错误";
    assert genericRecordDefinition.getProcessorStrategy().equals(processorStrategy) : "处理策略解析错误";
    assert genericRecordDefinition.getTemple().equals(temple) : "日志表达式解析错误";
    assert genericRecordDefinition.getName().equals("") : "定义名称解析错误";
    assert genericRecordDefinition.getTargetClass()
        .isAssignableFrom(RecordDefinitionBuilderTest.class) : "目标参数解析错误";

  }
}
